package com.arpangroup.user_service.processor;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.processor.level.model.LevelCalculationContext;

public class Level2Handler extends AbstractLevelHandler{
    @Override
    public Integer determineLevel(User user, LevelCalculationContext context) {
        if (user.getReserveBalance() >= 500 && user.getReserveBalance() <= 2000 &&
                context.getLevelACount() >= 4 && context.getLevelBCount() + context.getLevelCCount() >= 5) {
            return 2;
        }
        return nextLevel(user, context);
    }
}
