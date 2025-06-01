package com.arpangroup.referral_service.hierarchy.listener;

import com.arpangroup.nft_common.event.FirstDepositEvent;
import com.arpangroup.nft_common.event.UserRegisteredEvent;
import com.arpangroup.referral_service.audit.Audit;
import com.arpangroup.referral_service.hierarchy.service.UserHierarchyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)  // lower number means higher priority (executed earlier)
@Slf4j
public class UserHierarchyUpdateListener {
    private final UserHierarchyService userHierarchyService;

    /**
     * Handles user hierarchy update triggered by a user registration event.
     *
     * ⚠️ WARNING:
     * Avoid updating the hierarchy here unless you have verified the referee is an active user.
     * This method is NOT recommended for production unless there are strong validations in place,
     * because it may allow fake or inactive users to artificially inflate the rank of referrers.
     *
     * ⚠️ IMPORTANT:
     * DO NOT invoke both this method and the FirstDepositEvent-based method for the same user,
     * as it can lead to duplicate or incorrect entries in the closure table (UserHierarchy).
     *
     * @param event the UserRegisteredEvent containing referrer and referee information
     */
    @EventListener
    public void handleUpdateUserHierarchy(UserRegisteredEvent event) {
        log.info("Listening :: UserRegisteredEvent for userId: {}", event.getRefereeId());
        userHierarchyService.updateHierarchy(event.getReferrerId(), event.getRefereeId());
    }

    /**
     * Handles user hierarchy update when the referred user makes their first deposit.
     *
     * ✅ Recommended trigger point — this ensures the downline user is active and has contributed,
     * helping maintain integrity in rank calculations and bonus logic.
     *
     * ⚠️ IMPORTANT:
     * DO NOT invoke both this method and the UserRegisteredEvent-based method for the same user,
     * as it can lead to duplicate or invalid entries in the closure table (UserHierarchy).
     *
     * @param event the FirstDepositEvent containing the referrer's and user's information
     */
    @EventListener
    public void handleUpdateUserHierarchy(FirstDepositEvent event) {
        //log.info("Listening :: FirstDepositEvent for userId: {}", event.getUserId());
        //userHierarchyService.updateHierarchy(event.getReferrerId(), event.getReferrerId());
    }
}
