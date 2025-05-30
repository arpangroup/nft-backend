package com.arpangroup.referral_service.rank.evaluation;

import com.arpangroup.referral_service.hierarchy.UserHierarchyService;
import com.arpangroup.referral_service.rank.model.Rank;

public interface RankEvaluationStrategy {
    Rank getRank();
    //boolean isEligible(RankEvaluationContext context, UserHierarchyService hierarchyService);
    boolean isEligible(Long userId, RankEvaluationContext context);
}
