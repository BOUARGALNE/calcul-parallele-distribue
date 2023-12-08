package org.sid.walletservice.repositories;


import org.sid.walletservice.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency,Long> {
    Currency findByCode(String code);
    List<Currency> findByNameContains(String name);
}
