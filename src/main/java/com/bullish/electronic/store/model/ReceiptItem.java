package com.bullish.electronic.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceiptItem {
    private Product product;
    private long quantity;
    private double discountPerProduct;

}
