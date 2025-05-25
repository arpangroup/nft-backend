package com.arpangroup.user_service.service.income.referral.strategy;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.service.income.referral.ReferralBonusStrategy;

public class AccountActivationBonusStrategy implements ReferralBonusStrategy {
    @Override
    public boolean isEligible(User referee) {
        //return referee.isActivated();
        return false;
    }

    @Override
    public void applyBonus(User referrer, User referee) {
        // Apply bonus logic
    }
}
