package com.arpangroup.referral_service.service.strategy.impl;

import com.arpangroup.nft_common.dto.UserInfo;
import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import com.arpangroup.referral_service.service.strategy.AbstractReferralBonusStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("ACCOUNT_ACTIVATION")
public class AccountActivationBonusStrategy extends AbstractReferralBonusStrategy {
    @Override
    public boolean isEligible(UserInfo referee) {
        return referee.isActive();
    }

    @Override
    protected BigDecimal getBonusAmount(UserInfo referrer, UserInfo referee) {
        return getFixedBonusAmount();
    }

    @Override
    protected ReferralBonusTriggerType getTriggerType() {
        return ReferralBonusTriggerType.ACCOUNT_ACTIVATION;
    }
}
