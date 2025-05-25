package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.entity.config.DownlineTier;
import com.arpangroup.user_service.entity.config.TeamIncomeRebateConfig;
import com.arpangroup.user_service.entity.config.UplineLevel;
import com.arpangroup.user_service.repository.TeamIncomeRebateConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/configs")
@RequiredArgsConstructor
public class IncomeConfigController {
    private final TeamIncomeRebateConfigRepository repository;

    @GetMapping("/income/team")
    public ResponseEntity<List<TeamIncomeRebateConfig>> getTeamIncomeConfig() {
        return ResponseEntity.ok(repository.findAll());
    }


    @GetMapping("/income/team/pivot")
    public ResponseEntity<List<Map<String, Object>>> getPivotedTeamIncomeConfig() {
        List<TeamIncomeRebateConfig> configs = repository.findAll();

        // Group by DownlineTier -> (UplineLevel -> percentage)
        Map<DownlineTier, Map<UplineLevel, Double>> pivotMap = new LinkedHashMap<>();

        for (TeamIncomeRebateConfig config : configs) {
            DownlineTier tier = config.getDownlineTier();
            UplineLevel level = config.getUplineLevel();
            double percentage = config.getPercentage().doubleValue();

            pivotMap
                    .computeIfAbsent(tier, k -> new EnumMap<>(UplineLevel.class))
                    .put(level, percentage);
        }

        // Build structured pivot rows
        List<Map<String, Object>> result = new ArrayList<>();
        for (DownlineTier tier : DownlineTier.values()) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("downlineTier", tier.name());

            for (UplineLevel level : UplineLevel.values()) {
                double value = pivotMap
                        .getOrDefault(tier, Collections.emptyMap())
                        .getOrDefault(level, 0.0);
                row.put(level.name(), value);
            }

            result.add(row);
        }

        return ResponseEntity.ok(result);
    }

}
