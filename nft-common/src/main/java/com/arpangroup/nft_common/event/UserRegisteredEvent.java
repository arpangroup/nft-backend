package com.arpangroup.nft_common.event;

import com.arpangroup.nft_common.enums.TriggerType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegisteredEvent {
    private Long refereeId;
    private Long referrerId;
    private TriggerType triggerType;

    public UserRegisteredEvent(Long refereeId, Long referrerId, TriggerType triggerType) {
        this.refereeId = refereeId;
        this.referrerId = referrerId;
        this.triggerType = triggerType;
    }
}
