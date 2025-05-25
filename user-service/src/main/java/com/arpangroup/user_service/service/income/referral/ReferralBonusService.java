package com.arpangroup.user_service.service.income.referral;

import com.arpangroup.user_service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReferralBonusService {

    private final Map<String, ReferralBonusStrategy> strategies;

    @Autowired
    public ReferralBonusService(List<ReferralBonusStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(s -> s.getClass().getAnnotation(Component.class).value(), s -> s));
    }

    public void evaluateBonus(User referrer, User referee, String strategyKey) {
        ReferralBonusStrategy strategy = strategies.get(strategyKey);
        if (strategy != null && strategy.isEligible(referee)) {
            strategy.applyBonus(referrer, referee);
        }
    }
}
