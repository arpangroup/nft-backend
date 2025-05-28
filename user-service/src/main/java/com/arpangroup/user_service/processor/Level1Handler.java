package com.arpangroup.user_service.processor;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.service.mlm.context.LevelCalculationContext;

import java.math.BigDecimal;

public class Level1Handler extends AbstractLevelHandler{
    @Override
    public Integer determineLevel(User user, LevelCalculationContext context) {
        if (user.getWalletBalance().compareTo(BigDecimal.valueOf(50)) > 0 &&
                user.getWalletBalance().compareTo(BigDecimal.valueOf(100)) < 0) {
            return 1;
        }

        return nextLevel(user, context);
    }
}
