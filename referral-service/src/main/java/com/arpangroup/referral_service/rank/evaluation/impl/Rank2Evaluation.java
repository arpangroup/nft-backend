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
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class Rank2Evaluation implements RankEvaluationStrategy {
    private final RankConfigRepository rankConfigRepository;

    @Override
    public Rank getRank() {
        return Rank.RANK_2;
    }

    @Override
    public boolean isEligible(Long userId, RankEvaluationContext context) {
        RankConfig config = rankConfigRepository.findById(Rank.RANK_2).orElseThrow(() -> new IllegalStateException("Rank config not found for RANK_2"));

        BigDecimal balance = context.getWalletBalance();
        if (balance.compareTo(BigDecimal.valueOf(config.getMinWalletBalance())) < 0 ||
                balance.compareTo(BigDecimal.valueOf(config.getMaxWalletBalance())) > 0) {
            return false;
        }
        Map<Integer, Integer> required = config.getRequiredLevelCounts();
        for (Map.Entry<Integer, Integer> entry : required.entrySet()) {
            int depth = entry.getKey();
            int requiredCount = entry.getValue();

            List<Long> usersAtDepth = context.getDownlineByDepth().getOrDefault(depth, Collections.emptyList());
            if (usersAtDepth.size() < requiredCount) {
                return false;
            }
        }
        return true;
    }

}
