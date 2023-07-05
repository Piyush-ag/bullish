package com.bullish.electronic.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
    private String id;
    private long quantity;
    private double discountPercentage;

}




