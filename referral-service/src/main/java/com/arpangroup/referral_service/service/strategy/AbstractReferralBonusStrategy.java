package com.arpangroup.referral_service.service.strategy;

import com.arpangroup.referral_service.client.UserClient;
import com.arpangroup.referral_service.domain.entity.ReferralBonus;
import com.arpangroup.referral_service.domain.enums.BonusStatus;
import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import com.arpangroup.referral_service.dto.UserInfo;
import com.arpangroup.referral_service.repository.ReferralBonusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractReferralBonusStrategy implements ReferralBonusStrategy {
    protected final ReferralBonusRepository bonusRepository;
    protected final UserClient userClient;


    @Override
    public void applyBonus(UserInfo referrer, UserInfo referee) {
        log.info("applyBonus for referrer: {}, referee: {}......");
        BigDecimal bonusAmount = getBonusAmount(referrer, referee);

        referrer.setBalance(referrer.getBalance().add(bonusAmount));
        userClient.updateUser(referrer);

        ReferralBonus bonus = new ReferralBonus();
        bonus.setReferrerId(referrer.getId());
        bonus.setRefereeId(referee.getId());
        bonus.setBonusAmount(bonusAmount);
        bonus.setStatus(BonusStatus.APPROVED);
        bonus.setReferralBonusTriggerType(getTriggerType());
        bonus.setRemarks("Bonus awarded via " + getTriggerType());
        bonusRepository.save(bonus);
    }

    // Allow strategy to provide custom amount and trigger type
    protected abstract BigDecimal getBonusAmount(UserInfo referrer, UserInfo referee);
    protected abstract ReferralBonusTriggerType getTriggerType();
}
