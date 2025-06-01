package com.arpangroup.referral_service.listener;

import com.arpangroup.nft_common.event.ProductSoldEvent;
import com.arpangroup.referral_service.income.service.IncomeDistributionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductSoldEventListener {
    private final IncomeDistributionService incomeService;

    @EventListener
    @Transactional
    public void handleProductSold(ProductSoldEvent event) {
        log.info("Listening :: ProductSoldEvent for sellerId: {}, productId: {}, productValue: {}.....", event.getSellerId(), event.getProductId(), event.getProductValue());
        incomeService.distributeIncome(event.getSellerId(), event.getProductValue());
    }
}
