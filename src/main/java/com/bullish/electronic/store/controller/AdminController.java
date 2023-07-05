package com.bullish.electronic.store.controller;

import com.bullish.electronic.store.model.Discount;
import com.bullish.electronic.store.model.Product;
import com.bullish.electronic.store.service.DiscountService;
import com.bullish.electronic.store.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    private final DiscountService discountService;

    @Autowired
    public AdminController(ProductService productService, DiscountService discountService) {
        this.productService = productService;
        this.discountService = discountService;
    }

    @Operation(summary = "Create or update a product")
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@Parameter(description = "The ID of the product", example = "1234")
                                                 @RequestParam String productId,
                                                 @Parameter(description = "The Name of the product", example = "watch")
                                                 @RequestParam String productName,
                                                 @Parameter(description = "The Price of the product in USD", example = "180")
                                                 @RequestParam Double productPrice) {
        Product createdProduct = productService.createProduct(new Product(productId, productName, productPrice));
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a product by ID")
    @GetMapping("/products/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> getProduct(
            @Parameter(description = "The ID of the product", example = "1")
            @PathVariable("id") String productId) {
        Product product = productService.getProduct(productId);
        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Operation(summary = "Remove a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully removed"),
    })
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> removeProduct(
            @Parameter(description = "The ID of the product")
            @PathVariable String productId) {
        productService.removeProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Add discount deals for products")
    @PostMapping("/discounts/{productId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Discount successfully created"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Discount> createDiscount(@RequestBody Discount discount, @PathVariable("productId") String productId) {
        Product product = productService.getProduct(productId);
        if (product == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Discount createdDiscount = discountService.createDiscount(discount);
        product.getDiscount().add(createdDiscount.getId());
        return new ResponseEntity<>(createdDiscount, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a discount details by Discount ID")
    @GetMapping("/discounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the discount"),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseEntity<Discount> getDiscount(
            @Parameter(description = "The ID of the discount deal", example = "1")
            @RequestParam String discountId) {
        Discount discount = discountService.getDiscount(discountId);
        if (discount != null)
            return new ResponseEntity<>(discount, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Remove a discount by Discount ID")
    @DeleteMapping("/discounts/{productId}")
    public ResponseEntity<Void> removeDiscount(
            @Parameter(description = "The ID of the product")
            @PathVariable String discountId) {
        discountService.removeDiscount(discountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
