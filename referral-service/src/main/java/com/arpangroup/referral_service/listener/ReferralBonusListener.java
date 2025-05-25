package com.arpangroup.referral_service.listener;

import com.arpangroup.referral_service.service.ReferralBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class ReferralBonusListener {
    private final ReferralBonusService referralBonusService;

    @EventListener
    public void handleUserRegistered(UserRegisteredEvent event) {
        referralBonusService.evaluateBonus(event.getUserId());
    }
}
