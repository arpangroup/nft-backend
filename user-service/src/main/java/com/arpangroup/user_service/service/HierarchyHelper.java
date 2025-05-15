package com.arpangroup.user_service.service;

import com.arpangroup.user_service.entity.UserHierarchy;
import com.arpangroup.user_service.repository.UserHierarchyRepository;
import com.arpangroup.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HierarchyHelper {
    public static Map<Long, List<UserHierarchy>> buildHierarchy(List<UserHierarchy> paths) {
        return paths.stream().collect(Collectors.groupingBy(UserHierarchy::getAncestor));
    }

}
