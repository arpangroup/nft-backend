package com.arpangroup.referral_service.temp.processor;

import com.arpangroup.referral_service.temp.mlm.context.LevelCalculationContext;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.referral_service.temp.processor.level.LevelHandler;

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
