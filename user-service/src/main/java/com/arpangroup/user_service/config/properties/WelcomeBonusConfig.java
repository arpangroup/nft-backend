package com.arpangroup.user_service.config.properties;

import com.arpangroup.user_service.entity.config.CalculationType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//@PropertySource("classpath:configs/bonus-config.yml")
@ConfigurationProperties(prefix = "bonus.welcome")
@Data
public class WelcomeBonusConfig {
    private boolean enable;
    private CalculationType calculationType;
    private double flatAmount;
}
