package com.arpangroup.referral_service.controller;

import com.arpangroup.referral_service.income.dto.TeamRebateConfigDto;
import com.arpangroup.referral_service.income.entity.TeamRebateConfig;
import com.arpangroup.referral_service.income.service.TeamRebateConfigService;
import com.arpangroup.referral_service.rank.dto.RankConfigDto;
import com.arpangroup.referral_service.rank.service.RankConfigService;
import com.arpangroup.user_service.entity.config.TeamIncomeRebateConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/config/income")
@RequiredArgsConstructor
public class IncomeConfigRestController {
    private final RankConfigService rankConfigService;
    private final TeamRebateConfigService teamRebateConfigService;

    @GetMapping("/rank")
    public List<RankConfigDto> getRankConfig() {
        // Convert entity to DTO including requiredLevelCounts map
        return rankConfigService.getAllRankConfigs();
    }

    @PostMapping("/rank")
    public ResponseEntity<?> updateRank(@RequestBody List<RankConfigDto> updatedConfigs) {
        rankConfigService.updateRankConfigs(updatedConfigs);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/team")
    public List<TeamRebateConfig> getAllTeamConfigs() {
        return teamRebateConfigService.getAll();
    }

    @PutMapping("/team")
    public ResponseEntity<Void> updateTeamConfigs(@RequestBody List<TeamRebateConfig> updatedConfigs) {
        teamRebateConfigService.updateTeamConfigs(updatedConfigs);
        return ResponseEntity.ok().build();
    }
}
