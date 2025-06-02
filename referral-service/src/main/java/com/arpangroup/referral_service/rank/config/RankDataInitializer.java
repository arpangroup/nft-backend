package com.arpangroup.referral_service.rank.config;

import com.arpangroup.referral_service.rank.model.Rank;
import com.arpangroup.referral_service.rank.repository.RankConfigRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.arpangroup.referral_service.rank.model.Rank.*;

@Component
@RequiredArgsConstructor
public class RankDataInitializer {
    private final RankConfigRepository rankConfigRepository;

    @PostConstruct
    public void init() {
        // Rank 1: No level requirement
        RankConfig rank1 = new RankConfig(RANK_1, 50, 100, new BigDecimal("1.8"));

        // Rank 2: Level A = 3, Level B = 4, Level C = 1
        RankConfig rank2 = new RankConfig(RANK_2, 500, 2000, new BigDecimal("2.1"));
        rank2.getRequiredLevelCounts().put(1, 3); // Level A
        rank2.getRequiredLevelCounts().put(2, 4); // Level B
        rank2.getRequiredLevelCounts().put(3, 1); // Level C

        // Rank 3: Level A = 6, Level B = 18, Level C = 2
        RankConfig rank3 = new RankConfig(RANK_3, 2000, 5000, new BigDecimal("2.6"));
        rank3.getRequiredLevelCounts().put(1, 6);
        rank3.getRequiredLevelCounts().put(2, 18);
        rank3.getRequiredLevelCounts().put(3, 2);

        // Rank 4: Level A = 15, Level B = 30, Level C = 5
        RankConfig rank4 = new RankConfig(RANK_4, 5000, 10000, new BigDecimal("3.1"));
        rank4.getRequiredLevelCounts().put(1, 15);
        rank4.getRequiredLevelCounts().put(2, 30);
        rank4.getRequiredLevelCounts().put(3, 5);

        rankConfigRepository.saveAll(List.of(rank1, rank2, rank3, rank4));
    }
}
