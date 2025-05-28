package com.arpangroup.user_service.config.properties;

import com.arpangroup.user_service.entity.config.CalculationType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConfigurationProperties(prefix = "bonus.referral")
@Data
public class ReferralBonusConfig {
    private boolean enable;
    private CalculationType calculationType;
    private BigDecimal percentageRate;
    private BigDecimal flatAmount;
}
