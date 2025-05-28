package com.arpangroup.product_service.service;

import com.arpangroup.product_service.dto.ProductCreateRequest;
import com.arpangroup.product_service.entity.Category;
import com.arpangroup.product_service.entity.Product;
import com.arpangroup.product_service.entity.UserCollection;
import com.arpangroup.product_service.enums.TransactionStatus;
import com.arpangroup.product_service.exception.PurchaseException;
import com.arpangroup.product_service.mapper.ProductMapper;
import com.arpangroup.product_service.repository.CategoryRepository;
import com.arpangroup.product_service.repository.ProductRepository;
import com.arpangroup.product_service.repository.UserCollectionRepository;
import com.arpangroup.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserCollectionRepository collectionRepository;
    private final ProductMapper mapper;
    private final UserService userService;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductsByProductId(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new PurchaseException("productId not found"));
    }

    public Page<Product> getAllProductsWithPurchasedProducts(Long userId, Pageable pageable) {
        List<Product> allProducts = productRepository.findAll();
        List<Product> purchasedProducts = getAllPurchasedProductsByUserId(userId);

        // Create a Set of purchased product IDs
        Set<Long> purchasedProductIds = purchasedProducts.stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        // Update isPurchased flag using Java 8 forEach
        allProducts.forEach(product -> {
            if (purchasedProductIds.contains(product.getId())) {
                product.setPurchased(true);
                product.setTransactionStatus(TransactionStatus.PURCHASED);
            }
        });

        // Apply pagination manually
        int start = Math.toIntExact(pageable.getOffset());
        int end = Math.min((start + pageable.getPageSize()), allProducts.size());
        List<Product> pagedProducts = allProducts.subList(start, end);

        return new PageImpl<>(pagedProducts, pageable, allProducts.size());
    }

    public List<Product> getAllPurchasedProductsByUserId(Long userId) {
        List<UserCollection> collections = collectionRepository.findByUserIdAndStatus(userId, TransactionStatus.PURCHASED);
        List<Long> collectedProductIds = collections.stream().map(UserCollection::getProductId).toList();
        return productRepository.findAllByIdIn(collectedProductIds);
    }

    public Product createProduct(ProductCreateRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new PurchaseException("Invalid categoryId"));

        Product product = mapper.mapFrom(request, category);
        product = productRepository.save(product);
        return product;
    }

}
