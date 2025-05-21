package com.arpangroup.user_service.processor.level;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.processor.level.model.LevelCalculationContext;

public interface LevelHandler {
    Integer determineLevel(User user, LevelCalculationContext context);
    void setNext(LevelHandler next);
}
