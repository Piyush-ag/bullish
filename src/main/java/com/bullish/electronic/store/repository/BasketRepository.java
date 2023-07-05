package com.bullish.electronic.store.repository;

import com.bullish.electronic.store.model.Basket;
import com.bullish.electronic.store.model.Customer;
import com.bullish.electronic.store.model.Product;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BasketRepository {
    private final Map<String, Basket> baskets = new ConcurrentHashMap<>();;

    /**
     * Save the basket for the given customer.
     *
     * @param customerId The ID of the customer.
     * @param basket     The basket to be saved.
     */
    public void createBasket(String customerId, Basket basket) {
        baskets.put(customerId, basket);
    }

    /**
     * Get the basket for the given customer.
     *
     * @param customerId The ID of the customer.
     * @return The customer's basket.
     */
    public Basket getBasket(String customerId) {
        Basket basket = baskets.get(customerId);
        if (basket == null) {
            basket = new Basket(customerId);
            baskets.put(customerId, basket);
        }
        return baskets.get(customerId);
    }

    /**
     * clear the basket for the given customer.
     *
     * @param customerId The ID of the customer.
     */
    public void removeBasket(String customerId) {
        Basket basket = getBasket(customerId);
        basket.getItems().clear();
    }

    /**
     * Remove the product from the basket
     *
     * @param customerId
     * @param productId
     */
    public void removeFromBasket(String customerId, String productId) {
        Basket basket = baskets.get(customerId);
        basket.getItems().remove(productId);
    }
}
