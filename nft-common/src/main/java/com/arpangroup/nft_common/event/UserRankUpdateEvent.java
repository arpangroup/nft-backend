package com.arpangroup.nft_common.event;

import com.arpangroup.nft_common.enums.TriggerType;
import lombok.Getter;

@Getter
public class UserRankUpdateEvent {
    private final Long userId;
    private final TriggerType triggerType;

    public UserRankUpdateEvent(Long userId, TriggerType triggerType) {
        this.userId = userId;
        this.triggerType = triggerType;
    }
}
