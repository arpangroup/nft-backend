package com.arpangroup.nft_common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReferralTriggerEvent {
    private final Long userId;             // Referee ID
}
