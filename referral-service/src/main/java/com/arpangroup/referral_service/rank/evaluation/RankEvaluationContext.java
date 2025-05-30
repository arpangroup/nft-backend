package com.arpangroup.referral_service.rank.evaluation;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Getter
public class RankEvaluationContext {
    private final Long userId;
    private final BigDecimal walletBalance;

    // Cache to avoid duplicate DB queries
    private final Map<RankLevel, Set<Long>> rankUserMap = new EnumMap<>(RankLevel.class);

    public RankEvaluationContext(Long userId, BigDecimal walletBalance) {
        this.userId = userId;
        this.walletBalance = walletBalance;
    }

    public boolean hasCachedUsers(RankLevel level) {
        return rankUserMap.containsKey(level);
    }

    public Set<Long> getUsersForLevel(RankLevel level) {
        return rankUserMap.getOrDefault(level, Set.of());
    }

    public void cacheUsers(RankLevel level, Set<Long> users) {
        rankUserMap.put(level, users);
    }
}
