package com.bullish.electronic.store.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    private String customerId;
    private List<ReceiptItem> items;
    private double total;

}


