package com.arpangroup.user_service.processor.bonus.strategy;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.entity.config.CalculationType;

import java.math.BigDecimal;

public interface ReferralBonusStrategy {
    BigDecimal calculateBonus(User user);
    CalculationType getSupportedType();
}
