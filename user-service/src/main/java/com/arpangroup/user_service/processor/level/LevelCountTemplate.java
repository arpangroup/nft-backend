package com.arpangroup.user_service.processor.level;

import com.arpangroup.user_service.processor.level.model.LevelCalculationContext;
import com.arpangroup.user_service.processor.level.model.LevelCounts;

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
