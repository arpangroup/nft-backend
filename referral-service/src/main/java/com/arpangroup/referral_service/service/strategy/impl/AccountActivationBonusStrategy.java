package com.arpangroup.referral_service.service.strategy.impl;

import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import com.arpangroup.referral_service.service.strategy.AbstractReferralBonusStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("ACCOUNT_ACTIVATION")
public class AccountActivationBonusStrategy extends AbstractReferralBonusStrategy {
    @Override
    public boolean isEligible(User referee) {
        //return referee.isActivated();
        return false;
    }

    @Override
    protected BigDecimal getBonusAmount(User referrer, User referee) {
        return BigDecimal.valueOf(100); // Example amount
    }

    @Override
    protected ReferralBonusTriggerType getTriggerType() {
        return ReferralBonusTriggerType.ACCOUNT_ACTIVATION;
    }
}
