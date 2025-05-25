package com.arpangroup.nft_common.event;

import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegisteredEvent {
    private Long refereeId;
    private String referralCode;
    private ReferralBonusTriggerType referralBonusTriggerType;
}
