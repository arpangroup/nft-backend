package com.arpangroup.referral_service.service.strategy.impl;

import com.arpangroup.referral_service.service.strategy.ReferralBonusStrategy;

public class MinimumRequirementBonusStrategy implements ReferralBonusStrategy {

    @Override
    public boolean isEligible(User referee) {
        //return referee.getRank() >= Rank.BRONZE; // or check invites etc.
        return false;
    }

    @Override
    public void applyBonus(User referrer, User referee) {
        // Apply bonus
    }
}