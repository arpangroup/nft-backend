package com.arpangroup.referral_service;

import com.arpangroup.referral_service.client.UserClient;
import com.arpangroup.referral_service.hierarchy.UserHierarchy;
import com.arpangroup.referral_service.hierarchy.UserHierarchyRepository;
import com.arpangroup.referral_service.income.entity.IncomeHistory;
import com.arpangroup.referral_service.income.entity.TeamRebateConfig;
import com.arpangroup.referral_service.income.repository.IncomeHistoryRepository;
import com.arpangroup.referral_service.income.repository.TeamRebateConfigRepository;
import com.arpangroup.referral_service.income.service.IncomeDistributionService;
import com.arpangroup.referral_service.income.service.TeamCommissionService;
import com.arpangroup.referral_service.rank.config.RankConfig;
import com.arpangroup.referral_service.rank.model.Rank;
import com.arpangroup.referral_service.rank.repository.RankConfigRepository;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@EnableJpaRepositories(basePackages = "com.arpangroup")
@EntityScan(basePackages = "com.arpangroup")
@ComponentScan(basePackages = "com.arpangroup")
//@EnableConfigurationProperties({
//        ReferralBonusConfig.class,
//        WelcomeBonusConfig.class
//})
public class IncomeDistributionServiceTest {

    @Autowired
    private IncomeDistributionService incomeDistributionService;

    @Autowired
    private RankConfigRepository rankConfigRepository;

    @Autowired
    private TeamRebateConfigRepository teamRebateConfigRepository;

    @Autowired
    private IncomeHistoryRepository incomeHistoryRepository;

    @Autowired
    private UserHierarchyRepository userHierarchyRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private UserClient userClient;

    @Mock
    private TeamCommissionService teamCommissionService;


    @BeforeEach
    void setup() {
        incomeHistoryRepository.deleteAll();
        userHierarchyRepository.deleteAll();
        userRepository.deleteAll();
        teamRebateConfigRepository.deleteAll();
        rankConfigRepository.deleteAll();

        // Create users
        User u1 = new User("U1", Rank.RANK_3.getValue(), BigDecimal.valueOf(500));
        User u2 = new User("U2", Rank.RANK_2.getValue(), BigDecimal.valueOf(400));
        User u3 = new User("U3", Rank.RANK_2.getValue(), BigDecimal.valueOf(300));
        User u4_seller = new User("U4", Rank.RANK_2.getValue(), BigDecimal.valueOf(200));
        userRepository.saveAll(List.of(u1, u2, u3, u4_seller));

        // Setup hierarchy
        // Use saved users' IDs for hierarchy
        userHierarchyRepository.saveAll(List.of(
                new UserHierarchy(u1.getId(), u4_seller.getId(), 3), // U1 is level 3
                new UserHierarchy(u2.getId(), u4_seller.getId(), 2), // U2 is level 2
                new UserHierarchy(u3.getId(), u4_seller.getId(), 1)  // U3 is level 1
        ));

        // Setup Rank Configs
        rankConfigRepository.saveAll(List.of(
                new RankConfig(Rank.RANK_2, 0, 0, new BigDecimal("0.10")), // 10%
                new RankConfig(Rank.RANK_3, 0, 0, new BigDecimal("0.15")) // 15%
        ));

        // Setup Team Rebate Configs
        teamRebateConfigRepository.saveAll(List.of(
                new TeamRebateConfig(Rank.RANK_2, Map.of(
                        1, new BigDecimal("0.05"),
                        2, new BigDecimal("0.02")
                )),
                new TeamRebateConfig(Rank.RANK_3, Map.of(
                        1, new BigDecimal("0.06"),
                        2, new BigDecimal("0.03")
                ))
        ));
    }

    @Test
    void testDistributeIncomeFor3LevelUser() {
        // Seller (U3) sells product worth 100
        incomeDistributionService.distributeIncome(3L, new BigDecimal("100.00"));

        List<IncomeHistory> histories = incomeHistoryRepository.findAll();
        assertThat(histories).hasSize(3);

        // U3 gets 10% of 100 = 10.00
        IncomeHistory sellerIncome = histories.stream().filter(h -> h.getUserId().equals(3L)).findFirst().orElseThrow();
        assertThat(sellerIncome.getAmount()).isEqualByComparingTo("10.00");
        assertThat(sellerIncome.getType()).isEqualTo(IncomeHistory.IncomeType.DAILY);

        // U2 (depth 1) gets 5% of 10 = 0.50
        IncomeHistory u2Income = histories.stream().filter(h -> h.getUserId().equals(2L)).findFirst().orElseThrow();
        assertThat(u2Income.getAmount()).isEqualByComparingTo("0.50");
        assertThat(u2Income.getType()).isEqualTo(IncomeHistory.IncomeType.TEAM);

        // U1 (depth 2) gets 3% of 10 = 0.30 (uses RANK_3 config)
        IncomeHistory u1Income = histories.stream().filter(h -> h.getUserId().equals(1L)).findFirst().orElseThrow();
        assertThat(u1Income.getAmount()).isEqualByComparingTo("0.30");
        assertThat(u1Income.getType()).isEqualTo(IncomeHistory.IncomeType.TEAM);

        // Print summary
        histories.forEach(h -> {
            System.out.printf("UserId: %d, Type: %s, Amount: %s, SourceUserId: %d, Rank: %s, Note: %s%n",
                    h.getUserId(), h.getType(), h.getAmount(), h.getSourceUserId(), h.getSourceUserRank(), h.getNote());
        });
    }
}
