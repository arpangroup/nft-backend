package com.arpangroup.referral_service.rank.evaluation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class RankEvaluationContext {
    private Long userId;
    private final BigDecimal walletBalance;
    private final Map<Integer, List<Long>> downlineByDepth; // e.g., depth 1 -> [user1, user2]
}
