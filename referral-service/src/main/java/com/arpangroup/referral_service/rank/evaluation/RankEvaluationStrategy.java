package com.arpangroup.referral_service.rank.evaluation;

import com.arpangroup.referral_service.hierarchy.UserHierarchyService;
import com.arpangroup.referral_service.rank.model.Rank;

public interface RankEvaluationStrategy {
    boolean isEligible(RankEvaluationContext context, UserHierarchyService hierarchyService);
    Rank getRank();
}
