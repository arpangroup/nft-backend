package com.arpangroup.nft_core.appconfig.service;

import com.arpangroup.nft_core.appconfig.entity.AppConfig;
import com.arpangroup.nft_core.appconfig.repository.AppConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConfigService {
    private final AppConfigRepository repository;
    private final Map<String, String> configMap = new ConcurrentHashMap<>();
    private boolean loaded = false;

    public ConfigService(AppConfigRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void loadConfig() {
        if (!loaded) {
            repository.findAll().forEach(config ->
                    configMap.put(config.getKey(), config.getValue()));
            loaded = true;
        }
    }

    public List<AppConfig> getAllConfigs() {
        return repository.findAll();
    }

    public String get(String key) {
        return configMap.get(key);
    }

    public Optional<String> getOptional(String key) {
        return Optional.ofNullable(configMap.get(key));
    }

    // auto-convert values to int
    public int getInt(String key, int defaultValue) {
        String value = configMap.get(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // auto-convert values to boolean
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = configMap.get(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    // auto-convert values to double
    public double getDouble(String key, double defaultValue) {
        String value = configMap.get(key);
        try {
            return value != null ? Double.parseDouble(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
