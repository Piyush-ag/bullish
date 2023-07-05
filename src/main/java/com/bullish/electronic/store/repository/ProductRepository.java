package com.bullish.electronic.store.repository;


import com.bullish.electronic.store.model.Product;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ProductRepository {
    private final Map<String, Product> products = new ConcurrentHashMap<>();

    /**
     * Create a new product.
     *
     * @param product The product to be created.
     * @return The created product.
     */
    public Product createProduct(Product product) {
        products.put(product.getProductId(), product);
        return product;
    }

    /**
     * Get the product by ID.
     *
     * @param productId The ID of the product.
     * @return The product.
     */
    public Product getProduct(String productId) {
        return products.getOrDefault(productId, null);
    }

    /**
     * Remove the product by ID.
     *
     * @param productId The ID of the product.
     */
    public void removeProduct(String productId) {
        products.remove(productId);
    }
}

