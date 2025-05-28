package com.arpangroup.product_service.service;

import com.arpangroup.nft_common.event.ProductSoldEvent;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSellService {
    private final UserService userService;
    private final ProductRepository productRepository;
    private final UserCollectionRepository collectionRepository;
    private final ApplicationEventPublisher publisher;

    public UserCollection sell(ProductPurchaseOrSellRequest request) {
        log.info("Selling reserve for userId: {}, productId: {}.........", request.getUserId(), request.getProductId());
        // validate valid userId or not
        User user = userService.getUserById(request.getUserId());

        // validate valid productId or not
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new PurchaseException("invalid productId"));

        // purchase the product now
        UserCollection userCollection = collectionRepository.findByUserIdAndProductIdAndStatus(user.getId(), product.getId(), TransactionStatus.PURCHASED).orElseThrow(() -> new PurchaseException("Invalid request"));
        userCollection.setStatus(TransactionStatus.SOLD);
        userCollection = collectionRepository.save(userCollection);

        // Publish the event
        log.info("publishing ProductSoldEvent.....");
        publisher.publishEvent(new ProductSoldEvent(user.getId(), product.getId()));

        return userCollection;
    }


}
