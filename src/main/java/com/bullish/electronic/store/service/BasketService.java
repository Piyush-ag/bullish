package com.bullish.electronic.store.service;


import com.bullish.electronic.store.model.Basket;
import com.bullish.electronic.store.model.BasketItem;
import com.bullish.electronic.store.repository.BasketRepository;
import org.springframework.stereotype.Service;

@Service
public class BasketService {
    private final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    /**
     * Remove a product from the customer's basket.
     *
     * @param customerId the ID of the customer
     * @param productId  the ID of the product to remove
     */
    public void removeFromBasket(String customerId, String productId) {
        basketRepository.removeFromBasket(customerId, productId);
    }

    /**
     * Add a product to the customer's basket.
     *
     * @param customerId the ID of the customer
     * @param basketItem the item to add to the basket
     */
    public void addToBasket(String customerId, BasketItem basketItem) {
        Basket basket = basketRepository.getBasket(customerId);
        basket.getItems().put(basketItem.getProductId(), basketItem);
    }

    /**
     * Get the customer's basket. If the basket doesn't exist, create a new one.
     *
     * @param customerId the ID of the customer
     * @return the customer's basket
     */
    public Basket getBasket(String customerId) {
        return basketRepository.getBasket(customerId);
    }

    /**
     * Clean up all products from the customer's basket.
     *
     * @param customerId the ID of the customer
     */
    public void deleteBasket(String customerId) {
        basketRepository.removeBasket(customerId);
    }
}
