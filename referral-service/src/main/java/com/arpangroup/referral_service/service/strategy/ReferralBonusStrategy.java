package com.arpangroup.referral_service.service.strategy;

import com.arpangroup.referral_service.domain.entity.ReferralBonus;
import com.arpangroup.referral_service.dto.UserInfo;

/**
 * Referrer: The person who makes the referral.
 * Referee: The person who is being referred.
 */
public interface ReferralBonusStrategy {
    boolean isEligible(UserInfo referee);
    void applyBonus(UserInfo referrer, UserInfo referee);
    boolean processPendingBonus(ReferralBonus bonus);
}
