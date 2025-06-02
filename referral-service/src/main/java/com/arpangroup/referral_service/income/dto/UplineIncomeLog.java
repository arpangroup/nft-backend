package com.arpangroup.referral_service.income.dto;

import com.arpangroup.referral_service.rank.model.Rank;

import java.math.BigDecimal;

public record UplineIncomeLog(
        Long uplineUserId,
        Rank rank,
        int depth,
        BigDecimal percentage,
        BigDecimal income
) {
}
