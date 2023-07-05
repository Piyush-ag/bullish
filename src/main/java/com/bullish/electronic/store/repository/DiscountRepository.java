package com.bullish.electronic.store.repository;


import com.bullish.electronic.store.model.Discount;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DiscountRepository {
    private final Map<String, Discount> discounts = new ConcurrentHashMap<>();

    /**
     * Create a new discount.
     *
     * @param discount The discount to be created.
     * @return The created discount.
     */
    public Discount createDiscount(Discount discount) {
        discounts.put(discount.getId(), discount);
        return discount;
    }

    /**
     * Get the discount by ID.
     *
     * @param discountId The ID of the discount.
     * @return The discount.
     */
    public Discount getDiscount(String discountId) {
        return discounts.getOrDefault(discountId, null);
    }

    /**
     * Remove the discount by ID.
     *
     * @param discountId The ID of the discount.
     */
    public void removeDiscount(String discountId) {
        discounts.remove(discountId);
    }
}
