package com.bullish.electronic.store.controller;

import com.bullish.electronic.store.model.*;
import com.bullish.electronic.store.service.BasketService;
import com.bullish.electronic.store.service.CustomerService;
import com.bullish.electronic.store.service.ProductService;
import com.bullish.electronic.store.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final BasketService basketService;
    private final ReceiptService receiptService;
    private final ProductService productService;

    @Autowired
    public CustomerController(CustomerService customerService, BasketService basketService, ReceiptService receiptService,
                              ProductService productService) {
        this.customerService = customerService;
        this.basketService = basketService;
        this.receiptService = receiptService;
        this.productService = productService;
    }

    @Operation(summary = "Register a new customer or update an existing customer")
    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a customer by ID")
    @GetMapping("/{customerId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<Customer> getCustomer(
            @Parameter(description = "The ID of the customer")
            @PathVariable String customerId) {
        Customer customer = customerService.getCustomer(customerId);
        if (customer != null)
            return new ResponseEntity<>(customer, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Remove a customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer successfully removed"),
    })
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> removeCustomer(
            @Parameter(description = "The ID of the customer")
            @PathVariable String customerId) {
        customerService.removeCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get the customer's basket items")
    @GetMapping("/{customerId}/basket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the basket"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<Basket> getBasket(
            @Parameter(description = "The ID of the customer")
            @PathVariable String customerId) {
        Customer customer = customerService.getCustomer(customerId);
        Basket basket = basketService.getBasket(customerId);
        if (customer != null)
            return new ResponseEntity<>(basket, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Operation(summary = "Add items to the customer's basket")
    @PostMapping("/{customerId}/basket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items successfully added to the basket"),
            @ApiResponse(responseCode = "404", description = "Customer or Product not found")
    })
    public ResponseEntity<Void> addToBasket(
            @RequestBody BasketItem basketItem,
            @Parameter(description = "The ID of the customer")
            @PathVariable String customerId) {
        Customer customer = customerService.getCustomer(customerId);
        Product product = productService.getProduct(basketItem.getProductId());
        if (customer == null || product == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        basketService.addToBasket(customerId, basketItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Remove a product from the customer's basket")
    @DeleteMapping("/{customerId}/basket/{productId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully removed from the basket"),
            @ApiResponse(responseCode = "404", description = "Customer or Product not found")
    })
    public ResponseEntity<Void> removeFromBasket(
            @Parameter(description = "The ID of the customer")
            @PathVariable String customerId,
            @Parameter(description = "The ID of the product")
            @PathVariable String productId) {
        Customer customer = customerService.getCustomer(customerId);
        Product product = productService.getProduct(productId);
        if (customer == null || product == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        basketService.removeFromBasket(customerId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Remove all products from the customer's basket")
    @DeleteMapping("/{customerId}/basket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "All products successfully removed from the basket"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<Void> deleteBasket(
            @Parameter(description = "The ID of the customer")
            @PathVariable String customerId) {
        Customer customer = customerService.getCustomer(customerId);
        if (customer == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        basketService.deleteBasket(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Generate a receipt for the customer's basket")
    @GetMapping("/{customerId}/receipt")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receipt successfully generated"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<Receipt> generateReceipt(
            @Parameter(description = "The ID of the customer")
            @PathVariable String customerId) {
        Customer customer = customerService.getCustomer(customerId);
        if (customer == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Receipt receipt = receiptService.generateReceipt(customer);
        return new ResponseEntity<>(receipt, HttpStatus.OK);
    }
}
