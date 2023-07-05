package com.bullish.electronic.store.model;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Basket {
    // key productId, val-> quantity
    private Map<String, BasketItem> items;
    private String customerId;

    public Basket(String customerId) {
        this.customerId = customerId;
        this.items = new ConcurrentHashMap<>();
    }

}
