package com.arpangroup.user_service.processor.bonus.strategy.impl;

import com.arpangroup.user_service.config.properties.ReferralBonusConfig;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.entity.config.CalculationType;
import com.arpangroup.user_service.processor.bonus.strategy.ReferralBonusStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class FlatReferralBonusStrategy implements ReferralBonusStrategy {
    private final ReferralBonusConfig bonusProperties;

    @Override
    public BigDecimal calculateBonus(User user) {
        log.info("Calculating ReferralBonus for userID: {} using strategy: {}, WalletBalance: {}, bonusAmt: {}", user.getId(), getSupportedType(), user.getWalletBalance(), bonusProperties.getCalculationType());
        return bonusProperties.getFlatAmount();
    }

    @Override
    public CalculationType getSupportedType() {
        return CalculationType.FLAT;
    }
}
