package com.arpangroup.user_service.processor;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.processor.level.LevelHandler;
import com.arpangroup.user_service.processor.level.model.LevelCalculationContext;
import org.springframework.stereotype.Service;

@Service
public class LevelService {
    private final LevelHandler levelChain;

    public LevelService() {
        Level1Handler level1 = new Level1Handler();
        Level2Handler level2 = new Level2Handler();
        Level3Handler level3 = new Level3Handler();
        //Level4Handler level4 = new Level4Handler();

        level1.setNext(level2);
        level2.setNext(level3);
        //level3.setNext(level4);

        this.levelChain = level1;
    }

    public int determineLevel(User user) {
        LevelCalculationContext context = new LevelCalculationContext(); // precompute counts here
        context.setUserId(user.getId());
        return levelChain.determineLevel(user, context);
    }
}
