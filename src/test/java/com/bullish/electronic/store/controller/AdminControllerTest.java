package com.bullish.electronic.store.controller;
import com.bullish.electronic.store.model.Discount;
import com.bullish.electronic.store.model.Product;
import com.bullish.electronic.store.service.DiscountService;
import com.bullish.electronic.store.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private DiscountService discountService;

    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminController = new AdminController(productService, discountService);
    }

    @Test
    void createProduct() {
        String productId = "1234";
        String productName = "watch";
        double productPrice = 180.0;
        Product product = new Product(productId, productName, productPrice);
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = adminController.createProduct(productId, productName, productPrice);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(product);
        verify(productService).createProduct(any(Product.class));
    }

    @Test
    void getProduct() {
        String productId = "1234";
        Product product = new Product(productId, "watch", 180.0);
        when(productService.getProduct(productId)).thenReturn(product);

        ResponseEntity<Product> response = adminController.getProduct(productId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(product);
        verify(productService).getProduct(productId);
    }

    @Test
    void getProductNotFound() {
        String productId = "1234";
        when(productService.getProduct(productId)).thenReturn(null);

        ResponseEntity<Product> response = adminController.getProduct(productId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(productService).getProduct(productId);
    }

    @Test
    void removeProduct() {
        String productId = "1234";

        ResponseEntity<Void> response = adminController.removeProduct(productId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(productService).removeProduct(productId);
    }

    @Test
    void createDiscount() {
        String productId = "1234";
        String discountId = "5678";
        Discount discount = new Discount(discountId, 1, 10);
        Product product = new Product(productId, "watch", 180.0);
        when(productService.getProduct(productId)).thenReturn(product);
        when(discountService.createDiscount(discount)).thenReturn(discount);

        ResponseEntity<Discount> response = adminController.createDiscount(discount, productId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(discount);
        verify(productService).getProduct(productId);
        verify(discountService).createDiscount(discount);
        assertThat(product.getDiscount()).contains(discount.getId());
    }

    @Test
    void createDiscountProductNotFound() {
        String productId = "1234";
        String discountId = "5678";
        Discount discount = new Discount(discountId, 1, 10);
        when(productService.getProduct(productId)).thenReturn(null);

        ResponseEntity<Discount> response = adminController.createDiscount(discount, productId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(productService).getProduct(productId);
        verify(discountService, never()).createDiscount(any(Discount.class));
    }

    @Test
    void getDiscount() {
        String discountId = "1";
        Discount discount = new Discount(discountId, 1, 10);
        when(discountService.getDiscount(discountId)).thenReturn(discount);

        ResponseEntity<Discount> response = adminController.getDiscount(discountId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(discount);
        verify(discountService).getDiscount(discountId);
    }

    @Test
    void getDiscountNotFound() {
        String discountId = "1";
        when(discountService.getDiscount(discountId)).thenReturn(null);

        ResponseEntity<Discount> response = adminController.getDiscount(discountId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(discountService).getDiscount(discountId);
    }

    @Test
    void removeDiscount() {
        String discountId = "1";

        ResponseEntity<Void> response = adminController.removeDiscount(discountId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(discountService).removeDiscount(discountId);
    }
}
