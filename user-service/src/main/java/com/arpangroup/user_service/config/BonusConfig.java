package com.arpangroup.user_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@PropertySource("classpath:configs/bonus-config.yml")
//@ConfigurationProperties(prefix = "bonus.welcome")
public class BonusConfig {

    @Value("${bonus.welcome.enable}")
    public boolean isWelcomeBonusEnable;

    @Value("${bonus.welcome.amount}")
    public double welcomeBonusAmount;
}
