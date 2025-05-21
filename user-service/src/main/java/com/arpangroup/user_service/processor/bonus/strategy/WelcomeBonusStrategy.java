package com.arpangroup.user_service.processor.bonus.strategy;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.entity.config.CalculationType;

public interface WelcomeBonusStrategy {
    double calculateBonus(User user);
    CalculationType getSupportedType();
}
