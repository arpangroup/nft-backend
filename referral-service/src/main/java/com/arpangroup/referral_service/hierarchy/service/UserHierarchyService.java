package com.arpangroup.referral_service.hierarchy.service;

import com.arpangroup.user_service.dto.UserTreeNode;

import java.util.List;
import java.util.Map;

public interface UserHierarchyService {
    void updateHierarchy(Long referredId, Long newUserId);
    UserTreeNode getDownlineTree(Long rootUserId, int maxLevel);
    Map<Integer, List<Long>> getDownlinesGroupedByLevel(Long userId);
}
