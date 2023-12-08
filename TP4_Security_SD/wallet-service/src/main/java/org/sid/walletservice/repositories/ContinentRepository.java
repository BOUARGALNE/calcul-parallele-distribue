package org.sid.walletservice.repositories;

import org.sid.walletservice.entities.Continent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContinentRepository extends JpaRepository<Continent,Long> {
    Continent findByContinentName(String continentName);
}
