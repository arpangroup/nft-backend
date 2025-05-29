package com.arpangroup.referral_service.temp.processor.level;

import com.arpangroup.referral_service.temp.mlm.context.LevelCalculationContext;
import com.arpangroup.user_service.entity.User;

public interface LevelHandler {
    Integer determineLevel(User user, LevelCalculationContext context);
    void setNext(LevelHandler next);
}
