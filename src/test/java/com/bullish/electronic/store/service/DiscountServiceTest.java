package com.bullish.electronic.store.service;

import com.bullish.electronic.store.model.Discount;
import com.bullish.electronic.store.repository.DiscountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiscountServiceTest {
    @Mock
    private DiscountRepository discountRepository;

    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        discountService = new DiscountService(discountRepository);
    }

    @Test
    void testCreateDiscount() {
        // Arrange
        Discount discount = new Discount("product1", 2, 10.0);

        // Mock repository behavior
        when(discountRepository.createDiscount(discount)).thenReturn(discount);

        // Act
        Discount createdDiscount = discountService.createDiscount(discount);

        // Assert
        assertNotNull(createdDiscount);
        assertEquals(discount, createdDiscount);
        verify(discountRepository, times(1)).createDiscount(discount);
    }

    @Test
    void testRemoveDiscount() {
        // Arrange
        String productId = "product1";

        // Act
        discountService.removeDiscount(productId);

        // Assert
        verify(discountRepository, times(1)).removeDiscount(productId);
    }

    @Test
    void testGetDiscount() {
        // Arrange
        String productId = "product1";
        Discount expectedDiscount = new Discount(productId, 2, 10.0);

        // Mock repository behavior
        when(discountRepository.getDiscount(productId)).thenReturn(expectedDiscount);

        // Act
        Discount retrievedDiscount = discountService.getDiscount(productId);

        // Assert
        assertNotNull(retrievedDiscount);
        assertEquals(expectedDiscount, retrievedDiscount);
        verify(discountRepository, times(1)).getDiscount(productId);
    }
}
