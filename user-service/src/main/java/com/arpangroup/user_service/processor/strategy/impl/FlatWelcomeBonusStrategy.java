package com.arpangroup.user_service.processor.strategy.impl;

import com.arpangroup.user_service.config.properties.WelcomeBonusConfig;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.entity.config.CalculationType;
import com.arpangroup.user_service.processor.strategy.WelcomeBonusStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FlatWelcomeBonusStrategy implements WelcomeBonusStrategy {
    private final WelcomeBonusConfig bonusProperties;

    @Override
    public double calculateBonus(User user) {
        log.info("Calculating WelcomeBonus for userID: {} using strategy: {}, WalletBalance: {}, bonusAmt: {}", user.getId(), getSupportedType(), user.getReserveBalance(), bonusProperties.getCalculationType());
        return bonusProperties.getFlatAmount();
    }

    @Override
    public CalculationType getSupportedType() {
        return CalculationType.FLAT;
    }
}
