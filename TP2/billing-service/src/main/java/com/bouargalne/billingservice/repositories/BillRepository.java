package com.bouargalne.billingservice.repositories;

import com.bouargalne.billingservice.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.List;

@RepositoryRestResource
public interface BillRepository extends JpaRepository<Bill,Long> {
    /*=> ça marche pas
    @RestResource(path = "/getByCustomerId")
    List<Bill> getByCustomerId(@Param("customerId") Long customerId);
     */
    List<Bill> findByCustomerId(Long customerId);
}