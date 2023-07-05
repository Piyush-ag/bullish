package com.bullish.electronic.store.service;

import com.bullish.electronic.store.model.Basket;
import com.bullish.electronic.store.model.BasketItem;
import com.bullish.electronic.store.repository.BasketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BasketServiceTest {
    @Mock
    private BasketRepository basketRepository;

    private BasketService basketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        basketService = new BasketService(basketRepository);
    }

    @Test
    void testRemoveFromBasket() {
        // Arrange
        String customerId = "1";
        String productId = "100";

        // Act
        basketService.removeFromBasket(customerId, productId);

        // Assert
        verify(basketRepository, times(1)).removeFromBasket(customerId, productId);
    }

    @Test
    void testAddToBasket() {
        // Arrange
        String customerId = "1";
        BasketItem basketItem = new BasketItem("100", 2);

        Basket basket = new Basket(customerId);
        when(basketRepository.getBasket(customerId)).thenReturn(basket);

        // Act
        basketService.addToBasket(customerId, basketItem);

        // Assert
        assertEquals(1, basket.getItems().size());
        assertTrue(basket.getItems().containsKey("100"));
        assertEquals(basketItem, basket.getItems().get("100"));
    }

    @Test
    void testGetBasket() {
        // Arrange
        String customerId = "1";
        Basket expectedBasket = new Basket(customerId);
        when(basketRepository.getBasket(customerId)).thenReturn(expectedBasket);

        // Act
        Basket basket = basketService.getBasket(customerId);

        // Assert
        assertNotNull(basket);
        assertEquals(expectedBasket, basket);
        verify(basketRepository, times(1)).getBasket(customerId);
    }

    @Test
    void testDeleteBasket() {
        // Arrange
        String customerId = "1";

        // Act
        basketService.deleteBasket(customerId);

        // Assert
        verify(basketRepository, times(1)).removeBasket(customerId);
    }
}
