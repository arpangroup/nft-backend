package com.arpangroup.referral_service.service.strategy;

import com.arpangroup.referral_service.client.UserClient;
import com.arpangroup.referral_service.domain.entity.ReferralBonus;
import com.arpangroup.referral_service.domain.enums.BonusStatus;
import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import com.arpangroup.referral_service.dto.UserInfo;
import com.arpangroup.referral_service.repository.ReferralBonusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Slf4j
public abstract class AbstractReferralBonusStrategy implements ReferralBonusStrategy {
    @Autowired
    protected ReferralBonusRepository bonusRepository;
    @Autowired
    protected UserClient userClient;


    protected BigDecimal getFixedBonusAmount() {
        return BigDecimal.valueOf(100); // Example amount
    }


    @Override
    public void applyBonus(UserInfo referrer, UserInfo referee) {
        log.info("applyBonus for referrer: {}, referee: {}......", referrer.getId(), referrer.getId());
        BigDecimal bonusAmount = getBonusAmount(referrer, referee);

        referrer.setBalance(referrer.getBalance().add(bonusAmount));
        userClient.updateUserInfo(referrer);

        ReferralBonus bonus = new ReferralBonus();
        bonus.setReferrerId(referrer.getId());
        bonus.setRefereeId(referee.getId());
        bonus.setBonusAmount(bonusAmount);
        bonus.setStatus(BonusStatus.APPROVED);
        bonus.setTriggerType(getTriggerType());
        bonus.setRemarks("Bonus awarded via " + getTriggerType());
        bonusRepository.save(bonus);
    }

    @Override
    public boolean processPendingBonus(ReferralBonus bonus) {
        Long refereeId = bonus.getRefereeId();
        UserInfo referee = userClient.getUserInfo(refereeId);
        UserInfo referrer = userClient.getUserInfo(bonus.getReferrerId());

        if (!isEligible(referee)) return false;

        applyBonus(referrer, referee);
        return true;
    }

    // Allow strategy to provide custom amount and trigger type
    protected abstract BigDecimal getBonusAmount(UserInfo referrer, UserInfo referee);
    protected abstract ReferralBonusTriggerType getTriggerType();
}
