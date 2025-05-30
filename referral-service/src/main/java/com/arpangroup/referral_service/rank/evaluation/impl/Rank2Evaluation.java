package com.arpangroup.referral_service.rank.evaluation.impl;

import com.arpangroup.referral_service.hierarchy.UserHierarchyService;
import com.arpangroup.referral_service.rank.evaluation.RankEvaluationContext;
import com.arpangroup.referral_service.rank.evaluation.RankEvaluationStrategy;
import com.arpangroup.referral_service.rank.model.Rank;

public class Rank2Evaluation implements RankEvaluationStrategy {
    @Override
    public boolean isEligible(RankEvaluationContext context, UserHierarchyService hierarchyService) {
        return false;
    }

    @Override
    public Rank getRank() {
        return null;
    }
}
