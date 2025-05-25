package com.arpangroup.referral_service.listener;

import com.arpangroup.nft_common.event.ReferralTriggerEvent;
import com.arpangroup.referral_service.service.ReferralBonusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
@Slf4j
public class ReferralBonusListener {
    private final ReferralBonusService referralBonusService;

    @EventListener
    public void handleUserRegistered(ReferralTriggerEvent event) {
        log.info("handleUserRegistered for userId: {}.....", event.getUserId());
        referralBonusService.evaluateBonus(event.getUserId());
    }
}
