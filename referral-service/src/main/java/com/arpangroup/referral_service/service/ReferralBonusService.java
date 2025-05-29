package com.arpangroup.referral_service.service;

import com.arpangroup.nft_common.dto.UserInfo;
import com.arpangroup.referral_service.annotation.Audit;
import com.arpangroup.referral_service.client.UserClient;
import com.arpangroup.referral_service.constant.Remarks;
import com.arpangroup.referral_service.domain.entity.ReferralBonus;
import com.arpangroup.referral_service.domain.enums.BonusStatus;
import com.arpangroup.nft_common.enums.TriggerType;
import com.arpangroup.referral_service.repository.ReferralBonusRepository;
import com.arpangroup.referral_service.strategy.referral.ReferralBonusStrategy;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @Audit(action = "EVALUATE_BONUS")
    public void evaluateBonus(Long refereeId) {
        log.info("evaluateBonus for refereeId: {}", refereeId);
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

    @Audit(action = "EVALUATE_ALL_PENDING_BONUS")
    public void evaluateAllPendingBonuses() {
        log.info("evaluateAllPendingBonuses........");
        List<ReferralBonus> referralBonuses = bonusRepository.findByStatus(BonusStatus.PENDING);
        log.info("Total PENDING users: {}", referralBonuses.size());

        for (ReferralBonus bonus : referralBonuses) {
            log.info("Evaluating ReferralBonus for referrer: {}", bonus.getReferrerId());
            String strategyKey = bonus.getTriggerType().getLabel();
            ReferralBonusStrategy strategy = strategies.get(strategyKey);
            log.info("Evaluating ReferralBonus using strategy: {}", strategy);

            if (strategy != null) {
                boolean processed = strategy.processPendingBonus(bonus);
                if (processed) {
                    bonus.setStatus(BonusStatus.APPROVED);
                    bonus.setRemarks(Remarks.REFERRAL_BONUS);
                    log.info("Updating ReferralBonus to DB with status as: {}......", bonus.getStatus());
                    bonusRepository.save(bonus);
                }
            }
        }
    }

    @Audit(action = "CREATE_PENDING_BONUS")
    public void createPendingBonus(Long referrerId, Long refereeId, TriggerType triggerType) {
        log.info("creatingPendingBonus for referrerId: {}, refereeId: {}, triggerType: {}........", referrerId, refereeId, triggerType);
        ReferralBonus bonus = new ReferralBonus();
        bonus.setReferrerId(referrerId);
        bonus.setRefereeId(refereeId);
        bonus.setBonusAmount(BigDecimal.valueOf(100)); // or strategy-based
        bonus.setTriggerType(triggerType);
        bonus.setStatus(BonusStatus.PENDING);
        bonus.setCreatedAt(LocalDateTime.now());

        log.info("Creating ReferralBonus to DB for referrerId: {}, refereeId: {} with status as: {}....", referrerId, refereeId, bonus.getStatus());
        bonusRepository.save(bonus);
    }
}
