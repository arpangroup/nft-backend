package com.arpangroup.referral_service.service;

import com.arpangroup.referral_service.domain.entity.ReferralBonus;
import com.arpangroup.referral_service.domain.enums.BonusStatus;
import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import com.arpangroup.referral_service.dto.UserInfo;
import com.arpangroup.referral_service.repository.ReferralBonusRepository;
import com.arpangroup.referral_service.service.strategy.ReferralBonusStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReferralBonusService {
    private final Map<String, ReferralBonusStrategy> strategies;
    private final ReferralBonusRepository bonusRepository;

    @Autowired
    public ReferralBonusService(List<ReferralBonusStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(s -> s.getClass().getAnnotation(Component.class).value(), s -> s));
    }

    public void evaluateBonus(UserInfo referrer, UserInfo referee, String strategyKey) {
        ReferralBonusStrategy strategy = strategies.get(strategyKey);
        if (strategy != null && strategy.isEligible(referee)) {
            strategy.applyBonus(referrer, referee);
        }
    }

    public void createPendingBonus(Long referrerId, Long refereeId, ReferralBonusTriggerType triggerType) {
        ReferralBonus bonus = new ReferralBonus();
        bonus.setReferrerId(referrerId);
        bonus.setRefereeId(refereeId);
        bonus.setBonusAmount(BigDecimal.valueOf(100)); // or strategy-based
        bonus.setTriggerType(triggerType);
        bonus.setStatus(BonusStatus.PENDING);
        bonus.setCreatedAt(LocalDateTime.now());

        bonusRepository.save(bonus);
    }

    public void evaluateBonusForUser(Long refereeId, ReferralBonusTriggerType triggerType) {
        // Find the bonus for this user & trigger type
        Optional<ReferralBonus> optional = bonusRepository.findByRefereeIdAndTriggerTypeAndStatus(
                refereeId, triggerType, BonusStatus.PENDING
        );

        if (optional.isPresent()) {
            ReferralBonus bonus = optional.get();

            // Load referrer and referee data (via API or shared module)
            UserInfo referee = userClient.getUserInfo(refereeId);
            UserInfo referrer = userClient.getUserInfo(bonus.getReferrerId());

            // Evaluate and apply
            ReferralBonusStrategy strategy = strategies.get(referralBonusTriggerType.name());
            if (strategy.isEligible(referee)) {
                strategy.applyBonus(referrer, referee);
            }
        }
    }

    public void evaluateAllPendingBonuses() {
        List<ReferralBonus> referralBonuses = bonusRepository.findByStatus(BonusStatus.PENDING);

        for (ReferralBonus bonus : referralBonuses) {
            Long refereeId = bonus.getRefereeId(); // the user who triggered the referral

            if (bonus.getReferralBonusTriggerType() == ReferralBonusTriggerType.FIRST_DEPOSIT && userHasDeposited(refereeId)) {
                awardBonus(bonus);
            } else if (bonus.getReferralBonusTriggerType() == ReferralBonusTriggerType.ACCOUNT_ACTIVATION && isUserActivated(refereeId)) {
                awardBonus(bonus);
            }
            // Add other rules if needed
        }
    }

    private void awardBonus(ReferralBonus bonus) {
        User referrer = userRepository.findById(bonus.getReferrerId()).orElseThrow();
        referrer.setBalance(referrer.getBalance() + bonus.getBonusAmount());

        bonus.setStatus(BonusStatus.APPROVED);
        bonus.setRemarks("Bonus awarded successfully.");
        userRepository.save(referrer);
    }
}
