package com.arpangroup.referral_service.temp.processor.bonus.strategy;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.entity.config.CalculationType;

import java.math.BigDecimal;

public interface ReferralBonusStrategy {
    BigDecimal calculateBonus(User user);
    CalculationType getSupportedType();
}
