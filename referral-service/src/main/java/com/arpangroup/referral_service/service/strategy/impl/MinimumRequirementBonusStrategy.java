package com.arpangroup.referral_service.service.strategy.impl;

import com.arpangroup.nft_common.dto.UserInfo;
import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import com.arpangroup.referral_service.service.strategy.AbstractReferralBonusStrategy;

import java.math.BigDecimal;

import static com.arpangroup.referral_service.domain.enums.UserRank.BRONZE;

public class MinimumRequirementBonusStrategy extends AbstractReferralBonusStrategy {
    @Override
    public boolean isEligible(UserInfo referee) {
        return referee.getRank() >= BRONZE.getValue(); // or check invites etc.
    }

    @Override
    protected BigDecimal getBonusAmount(UserInfo referrer, UserInfo referee) {
        return getFixedBonusAmount();
    }

    @Override
    protected ReferralBonusTriggerType getTriggerType() {
        return null;
    }
}