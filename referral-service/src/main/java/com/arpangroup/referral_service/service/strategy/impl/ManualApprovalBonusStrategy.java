package com.arpangroup.referral_service.service.strategy.impl;

import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import com.arpangroup.referral_service.domain.entity.ReferralBonus;
import com.arpangroup.referral_service.dto.UserInfo;
import com.arpangroup.referral_service.service.strategy.AbstractReferralBonusStrategy;
import com.arpangroup.referral_service.service.strategy.ReferralBonusStrategy;

import java.math.BigDecimal;

public class ManualApprovalBonusStrategy extends AbstractReferralBonusStrategy {

    @Override
    public boolean isEligible(UserInfo referee) {
        return referee.isReferralApproved();
    }

    @Override
    protected BigDecimal getBonusAmount(UserInfo referrer, UserInfo referee) {
        return getFixedBonusAmount();
    }

    @Override
    protected ReferralBonusTriggerType getTriggerType() {
        return ReferralBonusTriggerType.MANUAL_APPROVAL;
    }

}
