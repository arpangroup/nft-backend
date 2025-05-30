package com.arpangroup.referral_service.rank.service;

import com.arpangroup.referral_service.temp.mlm.context.LevelCalculationContext;
import com.arpangroup.referral_service.temp.processor.Level1Handler;
import com.arpangroup.referral_service.temp.processor.Level2Handler;
import com.arpangroup.referral_service.temp.processor.Level3Handler;
import com.arpangroup.referral_service.temp.processor.level.LevelHandler;
import com.arpangroup.user_service.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankEvaluationService {
    private final LevelHandler levelChain;

    public RankEvaluationService() {
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
