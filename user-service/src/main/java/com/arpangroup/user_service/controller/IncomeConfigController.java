package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.entity.config.TeamIncomeRebateConfig;
import com.arpangroup.user_service.repository.TeamIncomeRebateConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/configs")
@RequiredArgsConstructor
public class IncomeConfigController {
    private final TeamIncomeRebateConfigRepository repository;

    @GetMapping("/income/team")
    public ResponseEntity<List<TeamIncomeRebateConfig>> getTeamIncomeConfig() {
        return ResponseEntity.ok(repository.findAll());
    }
}
