package com.arpangroup.nft_core.appconfig.controller;

import com.arpangroup.nft_core.appconfig.dto.AppConfigUpdateRequest;
import com.arpangroup.nft_core.appconfig.entity.AppConfig;
import com.arpangroup.nft_core.appconfig.repository.AppConfigRepository;
import com.arpangroup.nft_core.appconfig.service.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
