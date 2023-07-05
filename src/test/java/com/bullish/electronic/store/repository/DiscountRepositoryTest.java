package com.bullish.electronic.store.repository;

import com.bullish.electronic.store.model.Discount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountRepositoryTest {
    private DiscountRepository discountRepository;

    @BeforeEach
    void setUp() {
        discountRepository = new DiscountRepository();
    }

    @Test
    void createDiscount_CreatesNewDiscount() {
        // Arrange
        Discount discount = new Discount("123", 10, 10);

        // Act
        Discount createdDiscount = discountRepository.createDiscount(discount);

        // Assert
        assertNotNull(createdDiscount);
        assertEquals(discount, createdDiscount);
    }

    @Test
    void getDiscount_ReturnsExistingDiscount() {
        // Arrange
        Discount discount = new Discount("123", 10, 10);
        discountRepository.createDiscount(discount);

        // Act
        Discount retrievedDiscount = discountRepository.getDiscount("123");

        // Assert
        assertNotNull(retrievedDiscount);
        assertEquals(discount, retrievedDiscount);
    }

    @Test
    void getDiscount_ReturnsNullForNonExistingDiscount() {
        // Act
        Discount retrievedDiscount = discountRepository.getDiscount("456");

        // Assert
        assertNull(retrievedDiscount);
    }

    @Test
    void removeDiscount_RemovesExistingDiscount() {
        // Arrange
        Discount discount = new Discount("123", 10, 10);
        discountRepository.createDiscount(discount);

        // Act
        discountRepository.removeDiscount("123");

        // Assert
        assertNull(discountRepository.getDiscount("123"));
    }

    @Test
    void removeDiscount_DoesNotThrowExceptionForNonExistingDiscount() {
        // Act & Assert
        assertDoesNotThrow(() -> discountRepository.removeDiscount("456"));
    }
}
