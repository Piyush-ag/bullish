package com.bullish.electronic.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketItem {
    private String productId;
    private long quantity;
}
