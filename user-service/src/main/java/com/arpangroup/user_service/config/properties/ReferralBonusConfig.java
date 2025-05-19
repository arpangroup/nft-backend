package com.arpangroup.user_service.config.properties;

import com.arpangroup.user_service.entity.config.CalculationType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bonus.referral")
@Data
public class ReferralBonusConfig {
    private boolean enable;
    private CalculationType calculationType;
    private double percentageRate;
    private double flatAmount;
}
