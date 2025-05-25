package com.arpangroup.user_service.service.income.referral.strategy;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.service.income.referral.ReferralBonusStrategy;

public class ManualApprovalBonusStrategy implements ReferralBonusStrategy {

    @Override
    public boolean isEligible(User referee) {
        //return referee.isReferralApproved(); // or similar admin-flag
        return false;
    }

    @Override
    public void applyBonus(User referrer, User referee) {
        // Admin manually triggers this, so logic may be minimal
    }
}
