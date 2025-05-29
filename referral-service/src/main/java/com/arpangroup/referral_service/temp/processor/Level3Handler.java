package com.arpangroup.referral_service.temp.processor;

import com.arpangroup.referral_service.temp.mlm.context.LevelCalculationContext;
import com.arpangroup.user_service.entity.User;

import java.math.BigDecimal;

public class Level3Handler extends AbstractLevelHandler{
    @Override
    public Integer determineLevel(User user, LevelCalculationContext context) {
        if (user.getWalletBalance().compareTo(BigDecimal.valueOf(500)) > 0 &&
                user.getWalletBalance().compareTo(BigDecimal.valueOf(2000)) < 0 &&
                context.getLevelACount() >= 4 && context.getLevelBCount() + context.getLevelCCount() >= 5) {
            return 3;
        }

        return nextLevel(user, context);
    }
}
