package org.sid.walletservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.walletservice.enums.WalletTransactionType;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class WalletTransaction {
    @Id
    private String id;
    private long timestamp;
    private double amount;
    private double currencyPrice;
    @Enumerated(EnumType.STRING)
    private WalletTransactionType type;
    @ManyToOne
    private Wallet wallet;
    @ManyToOne
    private Wallet targetWallet;
}
