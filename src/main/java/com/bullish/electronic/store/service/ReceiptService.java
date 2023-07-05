package com.bullish.electronic.store.service;

import com.bullish.electronic.store.model.*;
import com.bullish.electronic.store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReceiptService {
    private final ProductRepository productRepository;
    private final DiscountService discountService;
    private final BasketService basketService;

    public ReceiptService(ProductRepository productRepository, DiscountService discountService, BasketService basketService) {
        this.productRepository = productRepository;
        this.discountService = discountService;
        this.basketService = basketService;
    }

    public Receipt generateReceipt(Customer customer) {
        Basket basket = basketService.getBasket(customer.getId());
        List<ReceiptItem> receiptItems = new ArrayList<>();

        for (Map.Entry<String, BasketItem> entry : basket.getItems().entrySet()) {
            String productId = entry.getKey();
            long quantity = entry.getValue().getQuantity();

            Product product = productRepository.getProduct(productId);
            double discount = calculateDiscount(product, quantity);

            ReceiptItem receiptItem = new ReceiptItem(product, quantity, discount);
            receiptItems.add(receiptItem);
        }

        double total = calculateTotal(receiptItems);

        return new Receipt(customer.getId(), receiptItems, total);
    }

    double calculateDiscount(Product product, long quantity) {
        double discountTotal = 0.0;

        for (String dis : product.getDiscount()) {
            Discount discount = discountService.getDiscount(dis);
            if (discount !=null && quantity >= discount.getQuantity()) {
                double productPrice = product.getPrice();
                double discountPercentage = discount.getDiscountPercentage() / 100.0;
                double discountAmount = productPrice * discountPercentage;
                discountTotal = Math.max(discountAmount, discountTotal);
            }
        }

        return discountTotal;
    }


    double calculateTotal(List<ReceiptItem> receiptItems) {
        double total = 0.0;

        for (ReceiptItem receiptItem : receiptItems) {
            total += (receiptItem.getProduct().getPrice() - receiptItem.getDiscountPerProduct()) * receiptItem.getQuantity();
        }

        return total;
    }
}
