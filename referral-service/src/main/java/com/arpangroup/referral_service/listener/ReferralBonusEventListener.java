package com.arpangroup.referral_service.listener;

import com.arpangroup.nft_common.event.ReferralTriggerEvent;
import com.arpangroup.nft_common.event.UserRegisteredEvent;
import com.arpangroup.referral_service.referral.ReferralBonusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReferralBonusEventListener {
    private final ReferralBonusService referralBonusService;

    /**
     * Handles the UserRegisteredEvent.
     *
     * This method does **not** immediately apply the referral bonus to the referrer.
     * Instead, it creates a **PENDING bonus record** which will later be evaluated
     * and applied by a scheduled job (BonusEvaluator), if the required conditions are met.
     *
     * The concept of a pending bonus helps prevent abuse. For example, if referral bonuses
     * were given immediately upon registration, users could create fake/inactive accounts
     * just to earn bonuses. By waiting for a meaningful action (e.g., a deposit),
     * the system ensures the bonus is earned legitimately.
     *
     * @param event the event containing user registration and referral data
     */
    @EventListener
    //@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserRegistered(UserRegisteredEvent event) {
        log.info("Listening :: UserRegisteredEvent for userId: {}", event.getRefereeId());
        referralBonusService.createPendingBonus(event.getReferrerId(), event.getRefereeId(), event.getTriggerType());
    }

    /**
     * Handles the ReferralTriggerEvent.
     *
     * This method **directly applies** the referral bonus to the referrer.
     * It does **not** create a pending bonus record.
     *
     * Useful when the triggering condition (e.g., successful first deposit)
     * is already fulfilled, and there is no need to wait for the scheduler
     * to evaluate and apply the bonus.
     *
     * @param event the event indicating a referral trigger condition has been met
     */
    @EventListener
    public void handleReferralTrigger(ReferralTriggerEvent event) {
        log.info("Listening :: ReferralTriggerEvent for userId: {}.....", event.getUserId());
        referralBonusService.evaluateBonus(event.getUserId());
    }
}
