package org.sid.walletservice.services;

import lombok.extern.slf4j.Slf4j;
import org.sid.walletservice.dto.CurrencyTransferResponse;
import org.sid.walletservice.dto.NewWalletTransferRequest;
import org.sid.walletservice.entities.Currency;
import org.sid.walletservice.entities.Wallet;
import org.sid.walletservice.entities.WalletTransaction;
import org.sid.walletservice.enums.WalletTransactionType;
import org.sid.walletservice.repositories.CurrencyRepository;
import org.sid.walletservice.repositories.WalletRepository;
import org.sid.walletservice.repositories.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.*;

@Service
@Transactional
@Slf4j
public class WalletServiceImpl {
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final CurrencyRepository currencyRepository;
    @Autowired
    private RestTemplate restTemplate;

    public WalletServiceImpl(WalletRepository walletRepository, WalletTransactionRepository walletTransactionRepository, CurrencyRepository currencyRepository) {
        this.walletRepository = walletRepository;
        this.walletTransactionRepository = walletTransactionRepository;
        this.currencyRepository = currencyRepository;
    }

    public List<Wallet> walletTransfer(String sourceWalletId, String destinationWalletId, double amount) throws Exception {
        final String baseUrl = "http://localhost:8084/currencyTransfer";
        URI uri = new URI(baseUrl);
        List<Wallet> walletList=new ArrayList<>();
        Wallet sourceWallet=walletRepository.findById(sourceWalletId)
                .orElseThrow(()->new RuntimeException(String.format("Wallet %s not found", sourceWalletId)));
        Wallet destinationWallet=walletRepository.findById(destinationWalletId)
                .orElseThrow(()->new RuntimeException(String.format("Wallet %s not found", sourceWalletId)));
        if(sourceWallet.getBalance()<amount) throw new RuntimeException(String.format("Balance Not sufficient, banlance=%f, amount=%f",sourceWallet.getBalance(),amount));
        NewWalletTransferRequest walletTransferRequest= NewWalletTransferRequest.builder()
                .sourceWalletId(sourceWalletId)
                .destinationWalletId(destinationWalletId)
                .sourceWalletCurrency(sourceWallet.getCurrency().getCode())
                .destinationWalletCurrency(destinationWallet.getCurrency().getCode())
                .amount(amount)
                .build();
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
// build the request
        HttpEntity<NewWalletTransferRequest> request = new HttpEntity<>(walletTransferRequest, headers);
        ResponseEntity<CurrencyTransferResponse> result = restTemplate.postForEntity(uri, request,CurrencyTransferResponse.class );
        CurrencyTransferResponse currencyTransferResponse=result.getBody();
        WalletTransaction debitWalletTransaction= WalletTransaction.builder()
                .id(currencyTransferResponse.getSourceTransactionId())
                .amount(amount)
                .currencyPrice(sourceWallet.getCurrency().getSalePrice())
                .timestamp(System.currentTimeMillis())
                .type(WalletTransactionType.DEBIT)
                .wallet(sourceWallet)
                .targetWallet(destinationWallet)
                .build();
        walletTransactionRepository.save(debitWalletTransaction);
        sourceWallet.setBalance(sourceWallet.getBalance()-amount);
        sourceWallet=walletRepository.save(sourceWallet);
        double convertedAmount=amount*(sourceWallet.getCurrency().getSalePrice()/destinationWallet.getCurrency().getPurchasePrice());
        WalletTransaction creditWalletTransaction= WalletTransaction.builder()
                .id(currencyTransferResponse.getDestinationTransactionId())
                .amount(convertedAmount)
                .currencyPrice(destinationWallet.getCurrency().getPurchasePrice())
                .timestamp(System.currentTimeMillis())
                .type(WalletTransactionType.CREDIT)
                .wallet(destinationWallet)
                .targetWallet(sourceWallet)
                .build();
        walletTransactionRepository.save(creditWalletTransaction);
        destinationWallet.setBalance(destinationWallet.getBalance()+convertedAmount);
        destinationWallet=walletRepository.save(destinationWallet);
        return Arrays.asList(sourceWallet,destinationWallet);
    }
    public Wallet newWallet(String currencyCode, double initialBalance, String userId){
        Currency currency=currencyRepository.findByCode(currencyCode);
        Wallet wallet= Wallet.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .currency(currency)
                .balance(initialBalance)
                .createdAt(System.currentTimeMillis())
                .build();
        return walletRepository.save(wallet);
    }
}
