package com.arpangroup.referral_service.strategy.referral.impl;

import com.arpangroup.nft_common.dto.UserInfo;
import com.arpangroup.nft_common.enums.TriggerType;
import com.arpangroup.referral_service.strategy.referral.AbstractReferralBonusStrategy;

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
    protected TriggerType getTriggerType() {
        return null;
    }
}