package com.bullish.electronic.store.service;

import com.bullish.electronic.store.model.Customer;
import com.bullish.electronic.store.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Create a new customer.
     *
     * @param customer the customer to create
     * @return the created customer
     */
    public Customer createCustomer(Customer customer) {
        return customerRepository.createCustomer(customer);
    }

    /**
     * Remove a customer.
     *
     * @param customerId the ID of the customer to remove
     */
    public Customer removeCustomer(String customerId) {
        return customerRepository.removeCustomer(customerId);
    }

    /**
     * Get a customer by ID.
     *
     * @param customerId the ID of the customer to retrieve
     * @return the customer with the specified ID
     */
    public Customer getCustomer(String customerId) {
        return customerRepository.getCustomer(customerId);
    }
}

