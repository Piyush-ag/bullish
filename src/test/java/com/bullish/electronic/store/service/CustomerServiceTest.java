package com.bullish.electronic.store.service;

import com.bullish.electronic.store.model.Customer;
import com.bullish.electronic.store.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    public static String email = "testemail@gmail.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void testCreateCustomer() {
        // Arrange
        Customer customer = new Customer("1", "John Doe", email);

        // Mock repository behavior
        when(customerRepository.createCustomer(customer)).thenReturn(customer);

        // Act
        Customer createdCustomer = customerService.createCustomer(customer);

        // Assert
        assertNotNull(createdCustomer);
        assertEquals(customer.getId(), createdCustomer.getId());
        assertEquals(customer.getName(), createdCustomer.getName());
        verify(customerRepository, times(1)).createCustomer(customer);
    }

    @Test
    void testRemoveCustomer() {
        // Arrange
        String customerId = "1";

        // Act
        customerService.removeCustomer(customerId);

        // Assert
        verify(customerRepository, times(1)).removeCustomer(customerId);
    }

    @Test
    void testGetCustomer() {
        // Arrange
        String customerId = "1";
        Customer expectedCustomer = new Customer(customerId, "John Doe", email);

        // Mock repository behavior
        when(customerRepository.getCustomer(customerId)).thenReturn(expectedCustomer);

        // Act
        Customer customer = customerService.getCustomer(customerId);

        // Assert
        assertNotNull(customer);
        assertEquals(expectedCustomer.getId(), customer.getId());
        assertEquals(expectedCustomer.getName(), customer.getName());
        verify(customerRepository, times(1)).getCustomer(customerId);
    }
}
