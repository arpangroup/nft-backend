package com.arpangroup.config_service.controller;

import com.arpangroup.config_service.repository.AppConfigRepository;
import com.arpangroup.config_service.service.ConfigService;
import com.arpangroup.config_service.dto.AppConfigUpdateRequest;
import com.arpangroup.config_service.entity.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/configs")
@RequiredArgsConstructor
@Slf4j
public class ConfigAdminController {
    private final ConfigService configService;
    private final AppConfigRepository configRepository;

    @GetMapping
    public ResponseEntity<List<AppConfig>> getALlConfigs() {
        return ResponseEntity.ok(configService.getAllConfigs());
    }

    @PostMapping("/reload")
    public ResponseEntity<String> reloadConfig() {
        log.info("reloadConfig.........");
        configService.loadConfig(); // or force reload
        return ResponseEntity.ok("Config reloaded");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateConfigs(@RequestBody List<AppConfigUpdateRequest> updates) {
        for (AppConfigUpdateRequest req : updates) {
            configRepository.findById(req.getKey()).ifPresent(config -> {
                config.setValue(req.getValue());
                configRepository.save(config);
            });
        }
        return ResponseEntity.ok("Configs updated");
    }
}
