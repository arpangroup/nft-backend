package com.arpangroup.referral_service.temp.mlm;

import com.arpangroup.referral_service.hierarchy.UserHierarchy;
import com.arpangroup.referral_service.hierarchy.UserHierarchyRepository;
import com.arpangroup.referral_service.temp.mlm.context.LevelCalculationContext;
import com.arpangroup.referral_service.temp.mlm.templates.LevelCountTemplate;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DefaultLevelCountService extends LevelCountTemplate {
    private final UserHierarchyRepository userHierarchyRepository;

    @Override
    protected void calculateLevelACount(LevelCalculationContext context) {
        List<Long> levelAIds = userHierarchyRepository.findByAncestorAndDepth(context.getUserId(), 1)
                .stream()
                .map(UserHierarchy::getDescendant)
                .toList();

        context.setLevelAIds(levelAIds);
        context.setLevelACount(levelAIds.size());
    }


    @Override
    protected void calculateLevelBCount(LevelCalculationContext context) {
        List<Long> levelAIds = context.getLevelAIds(); // reused
        List<Long> levelBIds = userHierarchyRepository.findByAncestorsAndDepth(levelAIds, 2)
                .stream()
                .map(UserHierarchy::getDescendant)
                .toList();

        context.setLevelBIds(levelBIds);
        context.setLevelBCount(levelBIds.size());
    }

    @Override
    protected void calculateLevelCCount(LevelCalculationContext context) {
        List<Long> levelBIds = context.getLevelBIds(); // reused
        List<Long> levelCIds = userHierarchyRepository.findByAncestorsAndDepth(levelBIds, 3)
                .stream()
                .map(UserHierarchy::getDescendant)
                .toList();

        context.setLevelCIds(levelCIds);
        context.setLevelCCount(levelCIds.size());
    }
}
