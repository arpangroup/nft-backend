package com.arpangroup.nft_common.event;

import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegisteredEvent {
    private Long refereeId;
    private String referralCode;
    private ReferralBonusTriggerType referralBonusTriggerType;

    public UserRegisteredEvent(Long refereeId, String referralCode, ReferralBonusTriggerType referralBonusTriggerType) {
        this.refereeId = refereeId;
        this.referralCode = referralCode;
        this.referralBonusTriggerType = referralBonusTriggerType;
    }
}
