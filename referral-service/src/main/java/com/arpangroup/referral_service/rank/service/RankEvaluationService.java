package com.arpangroup.referral_service.rank.service;

import com.arpangroup.referral_service.rank.model.Rank;

import java.math.BigDecimal;

public interface RankEvaluationService {
    Rank evaluateUserRank(Long userId, BigDecimal walletBalance);
    void evaluateAndUpdateUserRank(Long userId, BigDecimal walletBalance);
    void evaluateAndUpdateUserWithReferrerRank(Long userId, BigDecimal walletBalance, Long referrerId);
}
