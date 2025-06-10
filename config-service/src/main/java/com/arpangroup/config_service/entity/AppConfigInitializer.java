package com.arpangroup.config_service.entity;

import com.arpangroup.config_service.repository.AppConfigRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AppConfigInitializer {
    private final AppConfigRepository repository;

    @PostConstruct
    public void init() {
        List<AppConfig> configs = List.of(
                // bonus:
                new AppConfig("bonus.signup.enable", true),
                new AppConfig("bonus.signup.calculation-type", "FLAT", "FLAT, PERCENTAGE"),
                new AppConfig("bonus.signup.flat-amount", 100),
                new AppConfig("bonus.referral.enable", true),
                new AppConfig("bonus.referral.calculation-type", "FLAT", "FLAT, PERCENTAGE"),
                new AppConfig("bonus.referral.percentage-rate", 0.5, "FLAT, PERCENTAGE"),
                new AppConfig("bonus.referral.flat-amount", 300),
                // Email Config
                new AppConfig("mail.host", "smtp.gmail.com"),
                new AppConfig("mail.port", 587),
                new AppConfig("mail.username", "cloud.arpan@gmail.com"),
                new AppConfig("mail.password", "yrcu swoe gbpm lpwo"),
                new AppConfig("mail.smtp.auth", true),
                new AppConfig("mail.starttls.enable", true)
        );

        repository.saveAll(configs);
    }
}
