package com.bullish.electronic.store.service;

import com.bullish.electronic.store.model.Discount;
import com.bullish.electronic.store.repository.DiscountRepository;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    /**
     * Create a new discount.
     *
     * @param discount the discount to create
     * @return the created discount
     */
    public Discount createDiscount(Discount discount) {
        return discountRepository.createDiscount(discount);
    }

    /**
     * Remove a discount.
     *
     * @param discountId the ID of the discount to remove
     */
    public void removeDiscount(String discountId) {
        discountRepository.removeDiscount(discountId);
    }

    /**
     * Get a discount by ID.
     *
     * @param discountId the ID of the discount to retrieve
     * @return the discount with the specified ID
     */
    public Discount getDiscount(String discountId) {
        return discountRepository.getDiscount(discountId);
    }
}
