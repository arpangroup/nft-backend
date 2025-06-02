package com.arpangroup.referral_service.listener;

import com.arpangroup.nft_common.event.ProductSoldEvent;
import com.arpangroup.referral_service.income.service.IncomeDistributionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductSoldEventListener {
    private final IncomeDistributionService incomeService;

    @SneakyThrows
    @EventListener
    @Transactional
    public void handleProductSold(ProductSoldEvent event) {
        log.info("ProductSoldEvent :: Distribute Income for sellerId: {}, productId: {}, productValue: {}.....", event.getSellerId(), event.getProductId(), event.getProductValue());
        incomeService.distributeIncome(event.getSellerId(), event.getProductValue());
    }
}
