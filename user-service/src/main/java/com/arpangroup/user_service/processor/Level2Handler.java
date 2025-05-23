package com.arpangroup.user_service.processor;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.service.mlm.context.LevelCalculationContext;

public class Level2Handler extends AbstractLevelHandler{
    @Override
    public Integer determineLevel(User user, LevelCalculationContext context) {
        if (user.getWalletBalance() >= 500 && user.getWalletBalance() <= 2000 &&
                context.getLevelACount() >= 4 && context.getLevelBCount() + context.getLevelCCount() >= 5) {
            return 2;
        }
        return nextLevel(user, context);
    }
}
