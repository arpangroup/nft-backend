package com.arpangroup.referral_service.strategy.referral.impl;

import com.arpangroup.nft_common.dto.UserInfo;
import com.arpangroup.nft_common.enums.TriggerType;
import com.arpangroup.referral_service.strategy.referral.AbstractReferralBonusStrategy;
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
    protected TriggerType getTriggerType() {
        return TriggerType.FIRST_DEPOSIT;
    }
}
