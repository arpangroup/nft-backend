package com.arpangroup.referral_service.listener;

import com.arpangroup.nft_common.event.ProductSoldEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductSoldListener {

    @EventListener
    public void handleUserRegistered(ProductSoldEvent event) {
        log.info("Listening :: handleUserRegistered for userId: {}, productId: {}.....", event.getUserId(), event.getProductId());
    }
}
