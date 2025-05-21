package com.arpangroup.user_service.processor;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.processor.level.model.LevelCalculationContext;

public class Level1Handler extends AbstractLevelHandler{
    @Override
    public Integer determineLevel(User user, LevelCalculationContext context) {
        if (user.getReserveBalance() >= 50 && user.getReserveBalance() <= 100) {
            return 1;
        }
        return nextLevel(user, context);
    }
}
