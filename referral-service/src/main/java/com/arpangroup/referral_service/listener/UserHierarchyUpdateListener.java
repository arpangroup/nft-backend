package com.arpangroup.referral_service.listener;

import com.arpangroup.nft_common.event.UserRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserHierarchyUpdateListener {
    @EventListener
    public void handle(UserRegisteredEvent event) {
        // Update user_hierarchy table
    }
}
