package com.bullish.electronic.store.repository;

import com.bullish.electronic.store.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.bullish.electronic.store.service.CustomerServiceTest.email;
import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = new CustomerRepository();
    }

    @Test
    void createCustomer_CreatesNewCustomer() {
        // Arrange
        Customer customer = new Customer("123", "John Doe", email);

        // Act
        Customer createdCustomer = customerRepository.createCustomer(customer);

        // Assert
        assertNotNull(createdCustomer);
        assertEquals(customer, createdCustomer);
    }

    @Test
    void getCustomer_ReturnsExistingCustomer() {
        // Arrange
        Customer customer = new Customer("123", "John Doe", email);
        customerRepository.createCustomer(customer);

        // Act
        Customer retrievedCustomer = customerRepository.getCustomer("123");

        // Assert
        assertNotNull(retrievedCustomer);
        assertEquals(customer, retrievedCustomer);
    }

    @Test
    void getCustomer_ReturnsNullForNonExistingCustomer() {
        // Act
        Customer retrievedCustomer = customerRepository.getCustomer("456");

        // Assert
        assertNull(retrievedCustomer);
    }

    @Test
    void removeCustomer_RemovesExistingCustomer() {
        // Arrange
        Customer customer = new Customer("123", "John Doe", email);
        customerRepository.createCustomer(customer);

        // Act
        customerRepository.removeCustomer("123");

        // Assert
        assertNull(customerRepository.getCustomer("123"));
    }

    @Test
    void removeCustomer_DoesNotThrowExceptionForNonExistingCustomer() {
        // Act & Assert
        assertDoesNotThrow(() -> customerRepository.removeCustomer("456"));
    }
}
