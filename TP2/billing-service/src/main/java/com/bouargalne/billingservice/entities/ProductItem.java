package com.bouargalne.billingservice.entities;

import com.bouargalne.billingservice.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class ProductItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    private String productName;
    private long price;
    private long quantity;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Bill bill;
    private Long productId;
    @Transient //pour la persistence, cad dans la bas de donnée je l'ai pas
    private Product product;


}