package com.arpangroup.referral_service.temp.mlm.templates;

import com.arpangroup.referral_service.temp.mlm.context.LevelCalculationContext;
import com.arpangroup.referral_service.temp.mlm.model.LevelCounts;

public abstract class LevelCountTemplate {
    public final LevelCounts calculateAllLevels(Long userId) {
        LevelCalculationContext context = new LevelCalculationContext();
        context.setUserId(userId);

        calculateLevelACount(context);
        calculateLevelBCount(context);
        calculateLevelCCount(context);

        return new LevelCounts(
                context.getLevelACount(),
                context.getLevelBCount(),
                context.getLevelCCount()
        );
    }

    protected abstract void calculateLevelACount(LevelCalculationContext context);
    protected abstract void calculateLevelBCount(LevelCalculationContext context);
    protected abstract void calculateLevelCCount(LevelCalculationContext context);
}
