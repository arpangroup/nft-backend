package com.arpangroup.user_service.entity.config;

import com.arpangroup.user_service.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "config_individual_income")
@Data
@NoArgsConstructor
public class IndividualIncomeConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "min_reserv_amt", nullable = false)
    private double minReservAmt;

    @Column(name = "max_reserv_amt", nullable = false)
    private double maxReservAmt;

    @Column(name = "profit", nullable = false)
    private Double profit;

    @Enumerated(EnumType.STRING)
    @Column(name = "profit_frequency", nullable = false)
    private ProfitFrequency profitFrequency = ProfitFrequency.PER_DAY;

    @Enumerated(EnumType.STRING)
    @Column(name = "calculation_type", nullable = false)
    private CalculationType calculationType = CalculationType.PERCENTAGE;

    @Column(name = "txn_per_day", nullable = false)
    private Integer transactionsPerDay;


    @Column(name = "annualized_returns", nullable = false)
    private double annualizedReturns; // e.g., "657%"

    @Column(name = "lv_a_require")
    private int communityLvA; // Nullable for Level-1

    @Column(name = "lv_b_require")
    private int communityLvB;

    @Column(name = "lv_c_require")
    private int communityLvC;

    /*@OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "direct_referral_bonus", nullable = false)
    private Double directReferralBonus;

    @Column(name = "level_income_percent", nullable = false)
    private Double levelIncomePercent;

    @Column(name = "matching_bonus", nullable = true)
    private Double matchingBonus;*/

    public String getReservationRange() {
        return minReservAmt + "-" + maxReservAmt;
    }
}
