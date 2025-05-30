package com.arpangroup.referral_service.rank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class RankConfigDto {
    private String rank;
    private int minWalletBalance;
    private int maxWalletBalance;
    private Map<Integer, Integer> requiredLevelCounts;
}
