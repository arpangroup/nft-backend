package com.arpangroup.referral_service.controller;

import com.arpangroup.referral_service.service.UserHierarchyService;
import com.arpangroup.user_service.dto.UserTreeNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserDownlineTreeController {
    private final UserHierarchyService userHierarchyService;

    @GetMapping("/downline-tree/{userId}")
    public ResponseEntity<UserTreeNode> getDownlineTree(@PathVariable Long userId, @RequestParam(name = "maxLevel", defaultValue = "3") int maxLevel) {
        UserTreeNode tree = userHierarchyService.getDownlineTree(userId, maxLevel);
        return ResponseEntity.ok(tree);
    }

//    @GetMapping("/hierarchy")
//    public ResponseEntity<List<UserHierarchy>> userHierarch() {
//        return ResponseEntity.ok(userHierarchyRepository.findAll());
//    }
}
