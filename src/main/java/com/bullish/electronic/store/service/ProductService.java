package com.bullish.electronic.store.service;

import com.bullish.electronic.store.model.Product;
import com.bullish.electronic.store.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Create a new product.
     *
     * @param product the product to create
     * @return the created product
     */
    public Product createProduct(Product product) {
        return productRepository.createProduct(product);
    }

    /**
     * Remove a product.
     *
     * @param productId the ID of the product to remove
     */
    public void removeProduct(String productId) {
        productRepository.removeProduct(productId);
    }

    /**
     * Get a product by ID.
     *
     * @param productId the ID of the product to retrieve
     * @return the product with the specified ID
     */
    public Product getProduct(String productId) {
        return productRepository.getProduct(productId);
    }
}
