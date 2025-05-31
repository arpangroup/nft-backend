package com.arpangroup.referral_service.income.service;

import com.arpangroup.referral_service.income.repository.TeamRebateConfigRepository;
import com.arpangroup.referral_service.rank.model.Rank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamCommissionService {
    private final TeamRebateConfigRepository teamRebateConfigRepository;

    public BigDecimal getTeamCommissionPercentage(Rank rank, int depth) {
        log.info("getTeamCommissionPercentage for Rank: {}, depth: {}", rank,depth);
        /*
        // Replace with DB or config-based logic
        if (rank == Rank.RANK_2 && depth == 1) return BigDecimal.valueOf(0.12); // 12% Lv.A
        if (rank == Rank.RANK_2 && depth == 2) return BigDecimal.valueOf(0.05); // 5% Lv.B
        if (rank == Rank.RANK_2 && depth == 3) return BigDecimal.valueOf(0.02); // 2% Lv.C
        return BigDecimal.ZERO;
        */

        return teamRebateConfigRepository.findByRank(rank)
                .map(config -> config.getIncomePercentages().getOrDefault(depth, BigDecimal.ZERO))
                .orElse(BigDecimal.ZERO);
    }

}
