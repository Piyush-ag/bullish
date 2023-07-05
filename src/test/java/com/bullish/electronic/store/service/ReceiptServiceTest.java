package com.bullish.electronic.store.service;

import com.bullish.electronic.store.model.*;
import com.bullish.electronic.store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReceiptServiceTest {
    private ReceiptService receiptService;
    private ProductRepository productRepository;
    private DiscountService discountService;
    private BasketService basketService;
    private final static String EMAIL = "testEmail@gmail.com";

    @BeforeEach
    public void setup() {
        productRepository = mock(ProductRepository.class);
        discountService = mock(DiscountService.class);
        basketService = mock(BasketService.class);
        receiptService = new ReceiptService(productRepository, discountService, basketService);
    }

    @Test
    public void testGenerateReceipt() {
        Customer customer = new Customer("1", "John Doe", EMAIL );
        Basket basket = new Basket("1");
        basket.getItems().put("P1", new BasketItem("P1", 2));
        basket.getItems().put("P2", new BasketItem("P2", 1));

        Product product1 = new Product("P1", "Product 1", 10.0);
        product1.getDiscount().add("DISCOUNT1");
        Product product2 = new Product("P2", "Product 2", 20.0);
        product2.getDiscount().add("DISCOUNT2");

        Discount discount1 = new Discount("DISCOUNT1", 2, 10.0);
        Discount discount2 = new Discount("DISCOUNT2", 1, 5.0);

        when(basketService.getBasket(customer.getId())).thenReturn(basket);
        when(productRepository.getProduct("P1")).thenReturn(product1);
        when(productRepository.getProduct("P2")).thenReturn(product2);
        when(discountService.getDiscount("DISCOUNT1")).thenReturn(discount1);
        when(discountService.getDiscount("DISCOUNT2")).thenReturn(discount2);

        Receipt receipt = receiptService.generateReceipt(customer);

        assertEquals(customer.getId(), receipt.getCustomerId());
        assertEquals(2, receipt.getItems().size());
        assertEquals(2, receipt.getItems().get(0).getQuantity());
        assertEquals(1, receipt.getItems().get(1).getQuantity());
        assertEquals(1.0, receipt.getItems().get(0).getDiscountPerProduct());
        assertEquals(1.0, receipt.getItems().get(1).getDiscountPerProduct());
        assertEquals(37.0, receipt.getTotal());

        verify(basketService).getBasket(customer.getId());
        verify(productRepository, times(2)).getProduct(Mockito.anyString());
        verify(discountService, times(2)).getDiscount(Mockito.anyString());
    }

    @Test
    public void testCalculateDiscount() {
        Product product = new Product("P1", "Product 1", 10.0);
        product.getDiscount().add("DISCOUNT1");
        Discount discount1 = new Discount("DISCOUNT1", 2, 10.0);

        when(discountService.getDiscount("DISCOUNT1")).thenReturn(discount1);

        double discount = receiptService.calculateDiscount(product, 2);

        assertEquals(1.0, discount);

        verify(discountService).getDiscount("DISCOUNT1");
    }

    @Test
    public void testCalculateTotal() {
        Product product1 = new Product("P1", "Product 1", 10.0);
        Product product2 = new Product("P2", "Product 2", 20.0);

        ReceiptItem receiptItem1 = new ReceiptItem(product1, 2, 1.0);
        ReceiptItem receiptItem2 = new ReceiptItem(product2, 1, 0.0);

        double total = receiptService.calculateTotal(List.of(receiptItem1, receiptItem2));

        assertEquals(38.0, total);
    }
}
