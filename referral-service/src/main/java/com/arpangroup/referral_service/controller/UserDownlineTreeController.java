package com.arpangroup.referral_service.controller;

import com.arpangroup.nft_common.dto.UserInfo;
import com.arpangroup.referral_service.client.UserClient;
import com.arpangroup.referral_service.hierarchy.UserHierarchyService;
import com.arpangroup.referral_service.rank.model.Rank;
import com.arpangroup.referral_service.rank.service.RankEvaluationService;
import com.arpangroup.user_service.dto.UserTreeNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserDownlineTreeController {
    private final UserHierarchyService userHierarchyService;
    private final RankEvaluationService rankEvaluationService;
    private final UserClient userClient;

    @GetMapping("/downline-tree/{userId}")
    public ResponseEntity<UserTreeNode> getDownlineTree(@PathVariable Long userId, @RequestParam(name = "maxLevel", defaultValue = "3") int maxLevel) {
        UserTreeNode tree = userHierarchyService.getDownlineTree(userId, maxLevel);
        return ResponseEntity.ok(tree);
    }

    @GetMapping("/hierarchy/{userId}")
    public ResponseEntity<Map<Integer, List<Long>>> userHierarchy(@PathVariable Long userId) {
        return ResponseEntity.ok(userHierarchyService.getDownlinesGroupedByLevel(userId));
    }

    @GetMapping("/hierarchy/{userId}/statistics")
    public ResponseEntity<Map<String, Object>> getUserRank(@PathVariable Long userId) {
        UserInfo user = userClient.getUserInfo(userId);
        Rank rank = rankEvaluationService.evaluateUserRank(userId, user.getWalletBalance());
        Map<Integer, List<Long>> downlines = userHierarchyService.getDownlinesGroupedByLevel(userId);

        Map<String, Object> response = Map.of(
          "rank", rank,
          "downlines", downlines
        );

        return ResponseEntity.ok(response);
    }

//    @GetMapping("/hierarchy")
//    public ResponseEntity<List<UserHierarchy>> userHierarch() {
//        return ResponseEntity.ok(userHierarchyRepository.findAll());
//    }
}
