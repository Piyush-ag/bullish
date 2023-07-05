package com.bullish.electronic.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
public class Product {
    private String productId;
    private String name;
    private double price;
    private Set<String> discount;

    public Product(String productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.discount = ConcurrentHashMap.newKeySet();
    }
}



