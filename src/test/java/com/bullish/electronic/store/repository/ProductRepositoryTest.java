package com.bullish.electronic.store.repository;

import com.bullish.electronic.store.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void createProduct_CreatesNewProduct() {
        // Arrange
        Product product = new Product("123", "Product A", 10.0);

        // Act
        Product createdProduct = productRepository.createProduct(product);

        // Assert
        assertNotNull(createdProduct);
        assertEquals(product, createdProduct);
    }

    @Test
    void getProduct_ReturnsExistingProduct() {
        // Arrange
        Product product = new Product("123", "Product A", 10.0);
        productRepository.createProduct(product);

        // Act
        Product retrievedProduct = productRepository.getProduct("123");

        // Assert
        assertNotNull(retrievedProduct);
        assertEquals(product, retrievedProduct);
    }

    @Test
    void getProduct_ReturnsNullForNonExistingProduct() {
        // Act
        Product retrievedProduct = productRepository.getProduct("456");

        // Assert
        assertNull(retrievedProduct);
    }

    @Test
    void removeProduct_RemovesExistingProduct() {
        // Arrange
        Product product = new Product("123", "Product A", 10.0);
        productRepository.createProduct(product);

        // Act
        productRepository.removeProduct("123");

        // Assert
        assertNull(productRepository.getProduct("123"));
    }

    @Test
    void removeProduct_DoesNotThrowExceptionForNonExistingProduct() {
        // Act & Assert
        assertDoesNotThrow(() -> productRepository.removeProduct("456"));
    }
}
