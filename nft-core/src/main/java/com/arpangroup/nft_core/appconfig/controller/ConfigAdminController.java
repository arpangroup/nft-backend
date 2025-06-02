package com.arpangroup.nft_core.appconfig.controller;

import com.arpangroup.nft_core.appconfig.entity.AppConfig;
import com.arpangroup.nft_core.appconfig.service.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/configs")
@RequiredArgsConstructor
@Slf4j
public class ConfigAdminController {
    private final ConfigService configService;

    @GetMapping
    public ResponseEntity<List<AppConfig>> getALlConfigs() {
        return ResponseEntity.ok(configService.getAllConfigs());
    }

    @PostMapping("/reload")
    public ResponseEntity<String> reloadConfig() {
        configService.loadConfig(); // or force reload
        return ResponseEntity.ok("Config reloaded");
    }
}
