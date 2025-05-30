package com.arpangroup.referral_service.referral.impl;

import com.arpangroup.nft_common.dto.UserInfo;
import com.arpangroup.nft_common.enums.TriggerType;
import com.arpangroup.referral_service.referral.AbstractReferralBonusStrategy;
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
    protected TriggerType getTriggerType() {
        return TriggerType.ACCOUNT_ACTIVATION;
    }
}
