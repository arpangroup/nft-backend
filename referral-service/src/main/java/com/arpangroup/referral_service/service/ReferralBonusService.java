package com.arpangroup.referral_service.service;

import com.arpangroup.referral_service.client.UserClient;
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
    private final UserClient userClient;
    private final Map<String, ReferralBonusStrategy> strategies;
    private final ReferralBonusRepository bonusRepository;

    @Autowired
    public ReferralBonusService(List<ReferralBonusStrategy> strategyList, UserClient userClient, ReferralBonusRepository bonusRepository) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(s -> s.getClass().getAnnotation(Component.class).value(), s -> s));
        this.userClient= userClient;
        this.bonusRepository = bonusRepository;
    }

    public void evaluateBonus(UserInfo referrer, UserInfo referee, String strategyKey) {
        ReferralBonusStrategy strategy = strategies.get(strategyKey);
        if (strategy != null && strategy.isEligible(referee)) {
            strategy.applyBonus(referrer, referee);
        }
    }

    public void evaluateBonus(Long refereeId) {
        // Find the bonus for this user & trigger type
        Optional<ReferralBonus> optional = bonusRepository.findByRefereeIdAndStatus(refereeId, BonusStatus.PENDING);

        if (optional.isPresent()) {
            ReferralBonus bonus = optional.get();
            String strategy = bonus.getTriggerType().getLabel();

            // Load referrer and referee data (via API or shared module)
            UserInfo referee = userClient.getUserInfo(refereeId);
            UserInfo referrer = userClient.getUserInfo(bonus.getReferrerId());

            this.evaluateBonus(referrer, referee, strategy);
        }
    }

    public void evaluateAllPendingBonuses() {
        List<ReferralBonus> referralBonuses = bonusRepository.findByStatus(BonusStatus.PENDING);

        for (ReferralBonus bonus : referralBonuses) {
            String strategyKey = bonus.getTriggerType().getLabel();
            ReferralBonusStrategy strategy = strategies.get(strategyKey);

            if (strategy != null) {
                boolean processed = strategy.processPendingBonus(bonus);
                if (processed) {
                    bonus.setStatus(BonusStatus.APPROVED);
                    bonus.setRemarks("Bonus awarded via " + strategyKey);
                    bonusRepository.save(bonus);
                }
            }
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
}
