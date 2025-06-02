package com.arpangroup.referral_service.income.strategy;

import com.arpangroup.referral_service.income.dto.UplineIncomeLog;
import com.arpangroup.referral_service.rank.model.Rank;
import com.arpangroup.user_service.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TeamIncomeStrategy {
    List<UplineIncomeLog> distributeTeamIncome(Long sourceUserId, Rank sourceUserRank, BigDecimal baseIncome, List<User> uplines, Map<Long, Integer> uplineDepthMap);
}
