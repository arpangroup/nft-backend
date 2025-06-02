package com.arpangroup.referral_service.referral;

import com.arpangroup.nft_common.dto.UserInfo;
import com.arpangroup.nft_common.enums.TriggerType;
import com.arpangroup.referral_service.client.UserClient;
import com.arpangroup.referral_service.constant.Remarks;
import com.arpangroup.referral_service.referral.entity.BonusStatus;
import com.arpangroup.referral_service.referral.entity.ReferralBonus;
import com.arpangroup.referral_service.repository.ReferralBonusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

@Slf4j
public abstract class AbstractReferralBonusStrategy implements ReferralBonusStrategy {
    @Autowired
    protected ReferralBonusRepository bonusRepository;
    @Autowired
    protected UserClient userClient;

    @Value("${bonus.referral.flat-amount}")
    public int flatReferralBonus;


    protected BigDecimal getFixedBonusAmount() {
        log.info("flatReferralBonus: {}", flatReferralBonus);
        return BigDecimal.valueOf(flatReferralBonus);
    }


    @Override
    public void applyBonus(UserInfo referrer, UserInfo referee) {
        log.info("Apply Bonus for referrer: {}, referee: {}......", referrer.getId(), referrer.getId());
        BigDecimal bonusAmount = getBonusAmount(referrer, referee);
        depositReferralBonus(referrer.getId(), bonusAmount);

        ReferralBonus bonus = bonusRepository.findByReferrerIdAndRefereeIdAndStatus(referrer.getId(), referee.getId(), BonusStatus.PENDING).orElse(null);
        if (bonus == null) { // DIRECT APPROVE
            bonus = new ReferralBonus();
            bonus.setReferrerId(referrer.getId());
            bonus.setRefereeId(referee.getId());
            bonus.setBonusAmount(bonusAmount);
            bonus.setTriggerType(getTriggerType());
        }
        bonus.setStatus(BonusStatus.APPROVED);
        bonus.setRemarks(Remarks.REFERRAL_BONUS);
        bonusRepository.save(bonus);
    }

    @Override
    public boolean processPendingBonus(ReferralBonus bonus) {
        Long refereeId = bonus.getRefereeId();
        UserInfo referee = userClient.getUserInfo(refereeId);
        //UserInfo referrer = userClient.getUserInfo(bonus.getReferrerId());

        if (!isEligible(referee)) {
            return false;
        }

        depositReferralBonus(bonus.getReferrerId(), bonus.getBonusAmount());
        return true;
    }

    private void depositReferralBonus(long userId, BigDecimal bonusAmount) {
        userClient.deposit(userId, bonusAmount, Remarks.REFERRAL_BONUS, null);
    }

    // Allow strategy to provide custom amount and trigger type
    protected abstract BigDecimal getBonusAmount(UserInfo referrer, UserInfo referee);
    protected abstract TriggerType getTriggerType();
}
