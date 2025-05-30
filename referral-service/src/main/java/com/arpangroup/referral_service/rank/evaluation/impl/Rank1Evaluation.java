package com.arpangroup.referral_service.rank.evaluation.impl;

import com.arpangroup.referral_service.rank.config.RankConfig;
import com.arpangroup.referral_service.rank.evaluation.RankEvaluationContext;
import com.arpangroup.referral_service.rank.evaluation.RankEvaluationStrategy;
import com.arpangroup.referral_service.rank.model.Rank;
import com.arpangroup.referral_service.rank.repository.RankConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class Rank1Evaluation implements RankEvaluationStrategy {
    private final RankConfigRepository rankConfigRepository;

    @Override
    public Rank getRank() {
        return Rank.RANK_1;
    }

    @Override
    public boolean isEligible(Long userId, RankEvaluationContext context) {
        RankConfig config = rankConfigRepository.findById(Rank.RANK_2).orElseThrow(() -> new IllegalStateException("Rank config not found for RANK_2"));


        BigDecimal balance  = context.getWalletBalance();
        return balance.compareTo(BigDecimal.valueOf(config.getMinWalletBalance())) >= 0 &&
                balance.compareTo(BigDecimal.valueOf(config.getMaxWalletBalance())) <= 0;
        // No downline requirement for Rank 1
    }
}
