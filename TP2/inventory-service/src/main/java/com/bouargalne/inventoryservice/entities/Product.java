package com.bouargalne.inventoryservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity //pour JPA
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private long price;
    private long quantity;

    public Product(String name, long price, long quantity) {
        this.name=name;
        this.price=price;
        this.quantity=quantity;
    }
}