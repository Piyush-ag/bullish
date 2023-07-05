package com.bullish.electronic.store.controller;
import com.bullish.electronic.store.model.Basket;
import com.bullish.electronic.store.model.BasketItem;
import com.bullish.electronic.store.model.Customer;
import com.bullish.electronic.store.model.Product;
import com.bullish.electronic.store.model.Receipt;
import com.bullish.electronic.store.service.BasketService;
import com.bullish.electronic.store.service.CustomerService;
import com.bullish.electronic.store.service.ProductService;
import com.bullish.electronic.store.service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private BasketService basketService;

    @Mock
    private ReceiptService receiptService;

    @Mock
    private ProductService productService;

    private CustomerController customerController;
    private final static String EMAIL = "testingEmail@gmail.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerController = new CustomerController(
                customerService,
                basketService,
                receiptService,
                productService
        );
    }

    @Test
    void registerCustomer() {
        Customer customer = new Customer("1", "John Doe", EMAIL);
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.registerCustomer(customer);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(customer);
        verify(customerService).createCustomer(any(Customer.class));
    }

    @Test
    void getCustomer() {
        String customerId = "1";
        Customer customer = new Customer(customerId, "John Doe", EMAIL);
        when(customerService.getCustomer(customerId)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.getCustomer(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(customer);
        verify(customerService).getCustomer(customerId);
    }

    @Test
    void getCustomerNotFound() {
        String customerId = "1";
        when(customerService.getCustomer(customerId)).thenReturn(null);

        ResponseEntity<Customer> response = customerController.getCustomer(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(customerService).getCustomer(customerId);
    }

    @Test
    void removeCustomer() {
        String customerId = "1";

        ResponseEntity<Void> response = customerController.removeCustomer(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(customerService).removeCustomer(customerId);
    }

    @Test
    void getBasket() {
        String customerId = "1";
        Customer customer = new Customer(customerId, "John Doe", EMAIL);
        Basket basket = new Basket(customerId);
        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(basketService.getBasket(customerId)).thenReturn(basket);

        ResponseEntity<Basket> response = customerController.getBasket(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(basket);
        verify(customerService).getCustomer(customerId);
        verify(basketService).getBasket(customerId);
    }

    @Test
    void getBasketCustomerNotFound() {
        String customerId = "1";
        when(customerService.getCustomer(customerId)).thenReturn(null);

        ResponseEntity<Basket> response = customerController.getBasket(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(customerService).getCustomer(customerId);
    }

    @Test
    void addToBasket() {
        String customerId = "1";
        Customer customer = new Customer(customerId, "John Doe", EMAIL);
        Product product = new Product("P1", "Product 1", 10.0);
        BasketItem basketItem = new BasketItem("P1", 2);
        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(productService.getProduct(basketItem.getProductId())).thenReturn(product);

        ResponseEntity<Void> response = customerController.addToBasket(basketItem, customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(customerService).getCustomer(customerId);
        verify(productService).getProduct(basketItem.getProductId());
        verify(basketService).addToBasket(customerId, basketItem);
    }

    @Test
    void addToBasketCustomerNotFound() {
        String customerId = "1";
        BasketItem basketItem = new BasketItem("P1", 2);
        when(customerService.getCustomer(customerId)).thenReturn(null);

        ResponseEntity<Void> response = customerController.addToBasket(basketItem, customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(customerService).getCustomer(customerId);
        verify(basketService, never()).addToBasket(anyString(), any(BasketItem.class));
    }

    @Test
    void addToBasketProductNotFound() {
        String customerId = "1";
        Customer customer = new Customer(customerId, "John Doe", EMAIL);
        BasketItem basketItem = new BasketItem("P1", 2);
        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(productService.getProduct(basketItem.getProductId())).thenReturn(null);

        ResponseEntity<Void> response = customerController.addToBasket(basketItem, customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(customerService).getCustomer(customerId);
        verify(productService).getProduct(basketItem.getProductId());
        verify(basketService, never()).addToBasket(anyString(), any(BasketItem.class));
    }

    @Test
    void removeFromBasket() {
        String customerId = "1";
        String productId = "P1";
        Customer customer = new Customer(customerId, "John Doe", EMAIL);
        Product product = new Product(productId, "Product 1", 10.0);
        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(productService.getProduct(productId)).thenReturn(product);

        ResponseEntity<Void> response = customerController.removeFromBasket(customerId, productId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(customerService).getCustomer(customerId);
        verify(productService).getProduct(productId);
        verify(basketService).removeFromBasket(customerId, productId);
    }

    @Test
    void removeFromBasketCustomerNotFound() {
        String customerId = "1";
        String productId = "P1";
        when(customerService.getCustomer(customerId)).thenReturn(null);

        ResponseEntity<Void> response = customerController.removeFromBasket(customerId, productId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(customerService).getCustomer(customerId);
        verify(basketService, never()).removeFromBasket(anyString(), anyString());
    }

    @Test
    void removeFromBasketProductNotFound() {
        String customerId = "1";
        String productId = "P1";
        Customer customer = new Customer(customerId, "John Doe", EMAIL);
        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(productService.getProduct(productId)).thenReturn(null);

        ResponseEntity<Void> response = customerController.removeFromBasket(customerId, productId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(customerService).getCustomer(customerId);
        verify(productService).getProduct(productId);
        verify(basketService, never()).removeFromBasket(anyString(), anyString());
    }

    @Test
    void deleteBasket() {
        String customerId = "1";
        Customer customer = new Customer(customerId, "John Doe", EMAIL);
        when(customerService.getCustomer(customerId)).thenReturn(customer);

        ResponseEntity<Void> response = customerController.deleteBasket(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(customerService).getCustomer(customerId);
        verify(basketService).deleteBasket(customerId);
    }

    @Test
    void deleteBasketCustomerNotFound() {
        String customerId = "1";
        when(customerService.getCustomer(customerId)).thenReturn(null);

        ResponseEntity<Void> response = customerController.deleteBasket(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(customerService).getCustomer(customerId);
        verify(basketService, never()).deleteBasket(anyString());
    }

    @Test
    void generateReceipt() {
        String customerId = "1";
        Customer customer = new Customer(customerId, "John Doe", EMAIL);
        Receipt receipt = new Receipt();
        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(receiptService.generateReceipt(customer)).thenReturn(receipt);

        ResponseEntity<Receipt> response = customerController.generateReceipt(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(receipt);
        verify(customerService).getCustomer(customerId);
        verify(receiptService).generateReceipt(customer);
    }

    @Test
    void generateReceiptCustomerNotFound() {
        String customerId = "1";
        when(customerService.getCustomer(customerId)).thenReturn(null);

        ResponseEntity<Receipt> response = customerController.generateReceipt(customerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(customerService).getCustomer(customerId);
        verify(receiptService, never()).generateReceipt(any(Customer.class));
    }
}
