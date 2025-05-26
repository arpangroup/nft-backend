package com.arpangroup.user_service.service;

import com.arpangroup.user_service.dto.UserTreeNode;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.entity.UserHierarchy;
import com.arpangroup.user_service.repository.UserHierarchyRepository;
import com.arpangroup.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserHierarchyService {
    private final UserHierarchyRepository userHierarchyRepository;
    private final UserRepository userRepository;

    public void updateHierarchy(Long referredId, Long newUserId) {
        List<UserHierarchy> pathToSave = new ArrayList<>();

        // Insert direct path
        UserHierarchy directPath = new UserHierarchy(referredId, newUserId, 1);
        pathToSave.add(directPath);

        // Insert paths for all ancestors of the referrer, up to a maximum depth of 3
        List<UserHierarchy> ancestorPaths = userHierarchyRepository.findByDescendant(referredId);
        pathToSave.addAll(
                ancestorPaths.stream()
                        .filter(path -> path.getDepth() < 3) // Restrict to a maximum 3 levels
                        .map(path -> new UserHierarchy(path.getAncestor(), newUserId, path.getDepth() + 1))
                        .toList()
        );

        // Save all paths in a single batch operation
        userHierarchyRepository.saveAll(pathToSave);
    }

    public List<UserHierarchy> getDownline(Long userId) {
        return userHierarchyRepository.findByAncestor(userId).stream()
                .filter(path -> path.getDepth() <= 3)
                .collect(Collectors.toList());
    }


    public UserTreeNode getDownlineTree(Long rootUserId, int maxLevel) {
        User root = userRepository.findById(rootUserId).orElse(null);
        if (root == null) return null;

        UserTreeNode rootNode = new UserTreeNode(root.getId(), root.getUsername(), root.getWalletBalance());
        buildTreeRecursively(rootNode, 1, maxLevel); // max level 3
        return rootNode;
    }

    private void buildTreeRecursively(UserTreeNode parentNode, int currentLevel, int maxLevel) {
        if (currentLevel > maxLevel) return;

        List<UserHierarchy> directChildrenPaths = userHierarchyRepository.findByAncestorAndDepth(parentNode.getUserId(), 1);

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
