package com.arpangroup.product_catalog.controller;


import com.arpangroup.product_catalog.dto.ProductCreateRequest;
import com.arpangroup.product_catalog.entity.Product;
import com.arpangroup.product_catalog.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> products(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        if (userId != null && userId > 0) {
            return ResponseEntity.ok(productService.getAllProductsWithPurchasedProducts(userId, pageable));
        }
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> productsById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductsByProductId(productId));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

}
