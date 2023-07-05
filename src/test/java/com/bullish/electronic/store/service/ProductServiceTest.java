package com.bullish.electronic.store.service;
import com.bullish.electronic.store.model.Product;
import com.bullish.electronic.store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    void testCreateProduct() {
        // Arrange
        Product product = new Product("product1", "Product 1", 100.0);

        // Mock repository behavior
        when(productRepository.createProduct(product)).thenReturn(product);

        // Act
        Product createdProduct = productService.createProduct(product);

        // Assert
        assertNotNull(createdProduct);
        assertEquals(product, createdProduct);
        verify(productRepository, times(1)).createProduct(product);
    }

    @Test
    void testRemoveProduct() {
        // Arrange
        String productId = "product1";

        // Act
        productService.removeProduct(productId);

        // Assert
        verify(productRepository, times(1)).removeProduct(productId);
    }

    @Test
    void testGetProduct() {
        // Arrange
        String productId = "product1";
        Product expectedProduct = new Product(productId, "Product 1", 100.0);

        // Mock repository behavior
        when(productRepository.getProduct(productId)).thenReturn(expectedProduct);

        // Act
        Product retrievedProduct = productService.getProduct(productId);

        // Assert
        assertNotNull(retrievedProduct);
        assertEquals(expectedProduct, retrievedProduct);
        verify(productRepository, times(1)).getProduct(productId);
    }
}
