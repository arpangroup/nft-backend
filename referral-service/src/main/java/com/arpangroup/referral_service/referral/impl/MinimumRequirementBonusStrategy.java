package com.arpangroup.referral_service.referral.impl;

import com.arpangroup.nft_common.dto.UserInfo;
import com.arpangroup.nft_common.enums.TriggerType;
import com.arpangroup.referral_service.rank.evaluation.RankLevel;
import com.arpangroup.referral_service.referral.AbstractReferralBonusStrategy;

import java.math.BigDecimal;


public class MinimumRequirementBonusStrategy extends AbstractReferralBonusStrategy {
    @Override
    public boolean isEligible(UserInfo referee) {
        return referee.getRank() >= RankLevel.LEVEL_1.getValue(); // or check invites etc.
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