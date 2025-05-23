package com.arpangroup.user_service.processor;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.service.mlm.context.LevelCalculationContext;

public class Level1Handler extends AbstractLevelHandler{
    @Override
    public Integer determineLevel(User user, LevelCalculationContext context) {
        if (user.getWalletBalance() >= 50 && user.getWalletBalance() <= 100) {
            return 1;
        }
        return nextLevel(user, context);
    }
}
