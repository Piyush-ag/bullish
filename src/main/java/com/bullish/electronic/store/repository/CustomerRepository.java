package com.bullish.electronic.store.repository;


import com.bullish.electronic.store.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CustomerRepository {
    private final Map<String, Customer> customerIds;

    public CustomerRepository() {
        customerIds = new ConcurrentHashMap<>();
    }

    /**
     * Create a new customer.
     *
     * @param customer The customer to be created.
     * @return The created customer.
     */
    public Customer createCustomer(Customer customer) {
        customerIds.put(customer.getId(), customer);
        return customer;
    }

    /**
     * Get the customer by ID.
     *
     * @param customerId The ID of the customer.
     * @return The customer.
     */
    public Customer getCustomer(String customerId) {
        return customerIds.getOrDefault(customerId, null);
    }

    /**
     * Remove the customer by ID.
     *
     * @param customerId The ID of the customer.
     */
    public Customer removeCustomer(String customerId) {
        return customerIds.remove(customerId);
    }
}
