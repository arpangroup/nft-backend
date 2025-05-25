package com.arpangroup.user_service.service.income.referral;

import com.arpangroup.user_service.entity.User;

/**
 * Referrer: The person who makes the referral.
 * Referee: The person who is being referred.
 */
public interface ReferralBonusStrategy {
    boolean isEligible(User referee);
    void applyBonus(User referrer, User referee);
}
