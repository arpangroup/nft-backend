package com.arpangroup.nft_core.controller;

import com.arpangroup.referral_service.rank.dto.RankConfigDto;
import com.arpangroup.referral_service.rank.service.RankConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/config/rank")
@RequiredArgsConstructor
public class RankConfigRestController {
    private final RankConfigService rankConfigService;

    @GetMapping
    public List<RankConfigDto> getAll() {
        // Convert entity to DTO including requiredLevelCounts map
        return rankConfigService.getAllRankConfigs();
    }

    @PostMapping
    public ResponseEntity<?> update(@RequestBody List<RankConfigDto> updatedConfigs) {
        rankConfigService.updateRankConfigs(updatedConfigs);
        return ResponseEntity.ok().build();
    }
}
