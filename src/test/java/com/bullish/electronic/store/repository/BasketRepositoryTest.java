package com.bullish.electronic.store.repository;

import com.bullish.electronic.store.model.Basket;
import com.bullish.electronic.store.model.BasketItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasketRepositoryTest {
    private BasketRepository basketRepository;

    @BeforeEach
    void setUp() {
        basketRepository = new BasketRepository();
    }

    @Test
    void createBasket_SavesBasketForCustomer() {
        // Arrange
        String customerId = "123";
        Basket basket = new Basket(customerId);

        // Act
        basketRepository.createBasket(customerId, basket);

        // Assert
        assertEquals(basket, basketRepository.getBasket(customerId));
    }

    @Test
    void getBasket_ReturnsExistingBasketForCustomer() {
        // Arrange
        String customerId = "123";
        Basket basket = new Basket(customerId);
        basketRepository.createBasket(customerId, basket);

        // Act
        Basket retrievedBasket = basketRepository.getBasket(customerId);

        // Assert
        assertEquals(basket, retrievedBasket);
    }

    @Test
    void getBasket_CreatesNewBasketForNonExistingCustomer() {
        // Arrange
        String customerId = "123";

        // Act
        Basket retrievedBasket = basketRepository.getBasket(customerId);

        // Assert
        assertNotNull(retrievedBasket);
        assertEquals(customerId, retrievedBasket.getCustomerId());
        assertEquals(retrievedBasket, basketRepository.getBasket(customerId));
    }

    @Test
    void removeBasket_RemovesBasketForCustomer() {
        // Arrange
        String customerId = "123";
        Basket basket = new Basket(customerId);
        basketRepository.createBasket(customerId, basket);

        // Act
        basketRepository.removeBasket(customerId);

        // Assert
        assertTrue(basketRepository.getBasket(customerId).getItems().isEmpty());
    }

    @Test
    void removeFromBasket_RemovesProductFromBasket() {
        // Arrange
        String customerId = "123";
        String productId = "456";
        Basket basket = new Basket(customerId);
        basket.getItems().put(productId, new BasketItem(productId, 10));
        basketRepository.createBasket(customerId, basket);

        // Act
        basketRepository.removeFromBasket(customerId, productId);

        // Assert
        assertFalse(basketRepository.getBasket(customerId).getItems().containsKey(productId));
    }
}
