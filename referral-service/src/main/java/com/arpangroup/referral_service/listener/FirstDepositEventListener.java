package com.arpangroup.referral_service.listener;

import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import com.arpangroup.referral_service.service.ReferralBonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FirstDepositEventListener {
    private final ReferralBonusService referralBonusService;

    @EventListener
    public void handleFirstDeposit(FirstDepositEvent event) {
        Long userId = event.getUserId();

        // Use referralBonusService to evaluate bonus
        referralBonusService.evaluateBonusForUser(userId, ReferralBonusTriggerType.FIRST_DEPOSIT);
    }
}
