package com.arpangroup.user_service.service.income.referral.strategy;

import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.service.income.referral.ReferralBonusStrategy;

public class FirstDepositBonusStrategy implements ReferralBonusStrategy {
    @Override
    public boolean isEligible(User referee) {
        // return referee.getDeposits().size() == 1; // first deposit
        return false;
    }

    @Override
    public void applyBonus(User referrer, User referee) {
        // Apply bonus to referrer
    }
}
