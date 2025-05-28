package com.arpangroup.referral_service.service.strategy.impl;

import com.arpangroup.nft_common.dto.UserInfo;
import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import com.arpangroup.referral_service.service.strategy.AbstractReferralBonusStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("FIRST_DEPOSIT")
@Slf4j
public class FirstDepositBonusStrategy extends AbstractReferralBonusStrategy {
    @Override
    public boolean isEligible(UserInfo referee) {
        log.info("isEligible for firstDeposit for userId: {}", referee.getId());
        boolean hasDeposited = userClient.hasDeposit(referee.getId());
        log.info("userId: {}, hasDeposited: {}", referee.getId(), hasDeposited);
        return hasDeposited;
    }

    @Override
    protected BigDecimal getBonusAmount(UserInfo referrer, UserInfo referee) {
        return getFixedBonusAmount();
    }

    @Override
    protected ReferralBonusTriggerType getTriggerType() {
        return ReferralBonusTriggerType.FIRST_DEPOSIT;
    }
}
