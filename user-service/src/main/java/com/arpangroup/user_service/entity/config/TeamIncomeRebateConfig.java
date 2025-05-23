package com.arpangroup.user_service.entity.config;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "config_team_income_rate")
@Data
public class TeamIncomeRebateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "upline_level")
    private UplineLevel uplineLevel = UplineLevel.Level1; // e.g., "Level-2"

    @Enumerated(EnumType.STRING)
    @Column(name = "downline_tier")
    private DownlineTier downlineTier = DownlineTier.LvA; // e.g., "Lv.A"

    @Column(name = "percentage", precision = 5, scale = 4)
    private BigDecimal percentage; // e.g., 0.12

    public BigDecimal getPercentage() {
        if (percentage == null) {
            return BigDecimal.ZERO;
        }
        // If greater than 1 (e.g., 12), assume it's a percent and convert to fraction
        return percentage.compareTo(BigDecimal.ONE) > 0
                ? percentage.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
                : percentage;
    }
}
