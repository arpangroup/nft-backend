package com.arpangroup.referral_service.service;


import com.arpangroup.referral_service.domain.entity.UserHierarchy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HierarchyHelper {
    public static Map<Long, List<UserHierarchy>> buildHierarchy(List<UserHierarchy> paths) {
        return paths.stream().collect(Collectors.groupingBy(UserHierarchy::getAncestor));
    }

}
