package com.arpangroup.referral_service.listener;

import com.arpangroup.nft_common.event.ReferralTriggerEvent;
import com.arpangroup.referral_service.referral.impl.ReferralBonusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReferralTriggerEventListener {
    private final ReferralBonusService referralBonusService;


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
