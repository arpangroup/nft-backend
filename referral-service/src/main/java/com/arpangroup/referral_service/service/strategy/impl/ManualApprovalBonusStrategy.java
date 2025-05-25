package com.arpangroup.referral_service.service.strategy.impl;

import com.arpangroup.referral_service.service.strategy.ReferralBonusStrategy;

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
