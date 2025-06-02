package com.arpangroup.product_catalog.controller;

import com.arpangroup.product_catalog.dto.ProductPurchaseOrSellRequest;
import com.arpangroup.product_catalog.entity.Product;
import com.arpangroup.product_catalog.service.ProductSellService;
import com.arpangroup.product_catalog.service.ProductService;
import com.arpangroup.product_catalog.service.ProductPurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class PurchaseController {
    private final ProductPurchaseService productPurchaseService;
    private final ProductSellService productSellService;
    private final ProductService productService;

    @PostMapping("/purchase")
    public ResponseEntity<List<Product>> purchaseProduct(@Valid @RequestBody ProductPurchaseOrSellRequest request) {
        log.info("purchaseProduct for ProductID: {}, UserID: {}", request.getProductId(), request.getUserId());
        productPurchaseService.purchase(request);
        List<Product> purchasedProducts = productService.getAllPurchasedProductsByUserId(request.getUserId());
        return ResponseEntity.ok(purchasedProducts);
    }

    @PostMapping("/sell")
    public ResponseEntity<List<Product>> sellProduct(@Valid @RequestBody ProductPurchaseOrSellRequest request) {
        log.info("sellProduct for ProductID: {}, UserID: {}", request.getProductId(), request.getUserId());
        productSellService.sell(request);
        List<Product> purchasedProducts = productService.getAllPurchasedProductsByUserId(request.getUserId());
        return ResponseEntity.ok(purchasedProducts);
    }
}
