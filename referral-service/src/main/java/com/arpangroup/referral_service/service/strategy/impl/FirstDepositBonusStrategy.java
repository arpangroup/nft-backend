package com.arpangroup.referral_service.service.strategy.impl;

import com.arpangroup.referral_service.client.UserClient;
import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import com.arpangroup.referral_service.dto.UserInfo;
import com.arpangroup.referral_service.repository.ReferralBonusRepository;
import com.arpangroup.referral_service.service.strategy.AbstractReferralBonusStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("FIRST_DEPOSIT")
public class FirstDepositBonusStrategy extends AbstractReferralBonusStrategy {
    public FirstDepositBonusStrategy(ReferralBonusRepository bonusRepository, UserClient userClient) {
        super(bonusRepository, userClient);
    }

    @Override
    public boolean isEligible(UserInfo referee) {
        // return referee.getDeposits().size() == 1; // first deposit
        return false;
    }

    @Override
    protected BigDecimal getBonusAmount(UserInfo referrer, UserInfo referee) {
        return BigDecimal.valueOf(100); // Example amount
    }

    @Override
    protected ReferralBonusTriggerType getTriggerType() {
        return ReferralBonusTriggerType.FIRST_DEPOSIT;
    }
}
