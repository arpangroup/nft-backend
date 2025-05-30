package com.arpangroup.referral_service.rank.evaluation.impl;

import com.arpangroup.referral_service.rank.evaluation.RankEvaluationContext;
import com.arpangroup.referral_service.rank.evaluation.RankEvaluationStrategy;
import com.arpangroup.referral_service.rank.model.Rank;

public class Rank1Evaluation implements RankEvaluationStrategy {

    @Override
    public Rank getRank() {
        return Rank.RANK_1;
    }

    @Override
    public boolean isEligible(Long userId, RankEvaluationContext context) {
        return false;
    }
}
