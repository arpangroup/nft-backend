package com.arpangroup.referral_service.scheduler;

import com.arpangroup.referral_service.service.ReferralBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class BonusEvaluator {
    private final ReferralBonusService bonusService;

    @Scheduled(fixedRate = 86400000) // every 24 hours
    public void checkAndApplyBonuses() {
        bonusService.evaluateAllPendingBonuses();
    }
}
