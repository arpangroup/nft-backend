package com.arpangroup.user_service.processor;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.processor.level.LevelHandler;
import com.arpangroup.user_service.processor.level.model.LevelCalculationContext;

public abstract class AbstractLevelHandler implements LevelHandler {
    protected LevelHandler next;

    @Override
    public void setNext(LevelHandler next) {
        this.next = next;
    }

    protected Integer nextLevel(User user, LevelCalculationContext context) {
        return next != null ? next.determineLevel(user, context) : 0;
    }
}
