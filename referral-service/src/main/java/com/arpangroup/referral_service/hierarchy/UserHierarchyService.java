package com.arpangroup.referral_service.hierarchy;

import com.arpangroup.referral_service.rank.evaluation.RankEvaluationContext;
import com.arpangroup.referral_service.rank.evaluation.RankLevel;
import com.arpangroup.user_service.dto.UserTreeNode;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserHierarchyService {
    private final UserHierarchyRepository hierarchyRepo;
    private final UserRepository userRepository;

    public void updateHierarchy(Long referredId, Long newUserId) {
        List<UserHierarchy> pathToSave = new ArrayList<>();

        // Insert direct path
        UserHierarchy directPath = new UserHierarchy(referredId, newUserId, 1);
        pathToSave.add(directPath);

        // Insert paths for all ancestors of the referrer, up to a maximum depth of 3
        List<UserHierarchy> ancestorPaths = hierarchyRepo.findByDescendant(referredId);
        pathToSave.addAll(
                ancestorPaths.stream()
                        .filter(path -> path.getDepth() < 3) // Restrict to a maximum 3 levels
                        .map(path -> new UserHierarchy(path.getAncestor(), newUserId, path.getDepth() + 1))
                        .toList()
        );

        // Save all paths in a single batch operation
        hierarchyRepo.saveAll(pathToSave);
    }

    public Set<Long> getDirectReferrals(Long userId, int depth) {
        return hierarchyRepo.findByAncestorAndDepth(userId, depth).stream()
                .map(UserHierarchy::getDescendant)
                .collect(Collectors.toSet());
    }


    public Set<Long> calculateRankUsers(Long baseUserId, RankLevel level, RankEvaluationContext context) {
        if (level == RankLevel.LEVEL_1) {
            return getDirectReferrals(baseUserId, 1);
        }

        // Use cached data to build higher-level ranks
        RankLevel prevLevel = level.previous();
        Set<Long> previousRankUsers = context.getUsersForLevel(prevLevel);
        Set<Long> currentRankUsers = new HashSet<>();

        for (Long userId : previousRankUsers) {
            currentRankUsers.addAll(getDirectReferrals(userId, 1));
        }

        return currentRankUsers;
    }


    /*public List<UserHierarchy> getDownline(Long userId) {
        return userHierarchyRepository.findByAncestor(userId).stream()
                .filter(path -> path.getDepth() <= 3)
                .collect(Collectors.toList());
    }*/


    public UserTreeNode getDownlineTree(Long rootUserId, int maxLevel) {
        User root = userRepository.findById(rootUserId).orElse(null);
        if (root == null) return null;

        UserTreeNode rootNode = new UserTreeNode(root.getId(), root.getUsername(), root.getWalletBalance());
        buildTreeRecursively(rootNode, 1, maxLevel); // max level 3
        return rootNode;
    }

    private void buildTreeRecursively(UserTreeNode parentNode, int currentLevel, int maxLevel) {
        if (currentLevel > maxLevel) return;

        List<UserHierarchy> directChildrenPaths = hierarchyRepo.findByAncestorAndDepth(parentNode.getUserId(), 1);

        for (UserHierarchy path : directChildrenPaths) {
            Long childId = path.getDescendant();
            User childUser = userRepository.findById(childId).orElse(null);
            if (childUser != null) {
                UserTreeNode childNode = new UserTreeNode(childUser.getId(), childUser.getUsername(), childUser.getWalletBalance());
                parentNode.addChild(childNode);
                buildTreeRecursively(childNode, currentLevel + 1, maxLevel);
            }
        }
    }
}
