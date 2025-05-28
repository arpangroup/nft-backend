package com.arpangroup.product_service.service;

import com.arpangroup.product_service.dto.ProductPurchaseOrSellRequest;
import com.arpangroup.product_service.entity.Product;
import com.arpangroup.product_service.entity.UserCollection;
import com.arpangroup.product_service.enums.TransactionStatus;
import com.arpangroup.product_service.exception.PurchaseException;
import com.arpangroup.product_service.repository.ProductRepository;
import com.arpangroup.product_service.repository.UserCollectionRepository;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductPurchaseService {
    private final UserService userService;
    private final ProductRepository productRepository;
    private final UserCollectionRepository collectionRepository;

    public UserCollection purchase(ProductPurchaseOrSellRequest request) {
        // validate valid userId or not
        User user = userService.getUserById(request.getUserId());

        // validate valid productId or not
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new PurchaseException("invalid productId"));

        boolean isEligible = isUserEligibleToPurchase(user, product);
        if (!isEligible) throw new PurchaseException("user is not eligible to purchase this product");

        // purchase the product now
        UserCollection userCollection = new UserCollection(user.getId(), product.getId(), TransactionStatus.PURCHASED);
        userCollection = collectionRepository.save(userCollection);
        return userCollection;
    }

    private boolean isUserEligibleToPurchase(User user, Product product) {
        // based on users current package & reserve balance decide whether user is eligible to purchase or not
        return true;
    }

}
