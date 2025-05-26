package com.arpangroup.referral_service.listener;

import com.arpangroup.nft_common.event.UserRegisteredEvent;
import com.arpangroup.referral_service.client.UserClient;
import com.arpangroup.referral_service.dto.UserInfo;
import com.arpangroup.referral_service.service.ReferralBonusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRegisteredEventListener {
    private UserClient userClient;
    private ReferralBonusService bonusService;

    @EventListener
    public void handleUserRegistered(UserRegisteredEvent event) {
        log.info("Listening :: handleUserRegistered for userId: {}", event.getRefereeId());
        UserInfo referrer = userClient.getUserByReferralCode(event.getReferralCode());
        if (referrer != null) {
            //ReferralBonusTriggerType triggerType = ReferralBonusTriggerType.fromLabel(event.getReferralBonusTriggerType());
            bonusService.createPendingBonus(referrer.getId(), event.getRefereeId(), event.getReferralBonusTriggerType());
        }
    }
}
