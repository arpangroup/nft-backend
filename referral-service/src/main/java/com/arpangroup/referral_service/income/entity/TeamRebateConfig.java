package com.arpangroup.referral_service.income.entity;

import com.arpangroup.referral_service.rank.model.Rank;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "config_team_income")
@Data
@NoArgsConstructor
public class TeamRebateConfig {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "rank_type")
    private Rank rank;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "team_income_percentages", joinColumns = @JoinColumn(name = "rank_type"))
    @MapKeyColumn(name = "level") // 1 = A, 2 = B, 3 = C
    @Column(name = "percentage")
    private Map<Integer, BigDecimal> incomePercentages = new HashMap<>();

    public TeamRebateConfig(Rank rank, Map<Integer, BigDecimal> incomePercentages) {
        this.rank = rank;
        this.incomePercentages = incomePercentages;
    }
}
