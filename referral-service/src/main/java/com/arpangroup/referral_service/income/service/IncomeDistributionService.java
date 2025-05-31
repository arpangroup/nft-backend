package com.arpangroup.referral_service.income.service;

import com.arpangroup.referral_service.hierarchy.UserHierarchy;
import com.arpangroup.referral_service.hierarchy.UserHierarchyRepository;
import com.arpangroup.referral_service.income.entity.IncomeHistory;
import com.arpangroup.referral_service.income.repository.IncomeHistoryRepository;
import com.arpangroup.referral_service.income.repository.TeamIncomeConfigRepository;
import com.arpangroup.referral_service.income.strategy.TeamIncomeStrategy;
import com.arpangroup.referral_service.rank.config.RankConfig;
import com.arpangroup.referral_service.rank.model.Rank;
import com.arpangroup.referral_service.rank.repository.RankConfigRepository;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class IncomeDistributionService {
    private final UserRepository userRepository;
    private final RankConfigRepository rankConfigRepo;
    private final TeamIncomeConfigRepository teamIncomeRepo;
    private final UserHierarchyRepository hierarchyRepo;
    private final IncomeHistoryRepository incomeRepo;
    private final TeamIncomeStrategy teamIncomeStrategy;

    public void distributeIncome(Long sellerId, BigDecimal saleAmount) {
        log.info("distributeIncome for sellerId: {}, productValue: {}.........", sellerId, saleAmount);
        User seller = userRepository.findById(sellerId).orElseThrow(() -> new IllegalArgumentException("User not found: " + sellerId));
        Rank sellerRank = Rank.fromValue(seller.getRank());
        log.info("seller userId: {}, Rank: {}", sellerId, sellerRank);
        RankConfig config  = rankConfigRepo.findById(sellerRank).orElseThrow(() -> new IllegalStateException("Rank config not found: " + sellerRank));

        //BigDecimal profitRate = config.getCommissionRate().divide(BigDecimal.valueOf(100)
        BigDecimal profitRate = config.getCommissionRate();
        BigDecimal dailyIncome = saleAmount.multiply(profitRate).setScale(4, BigDecimal.ROUND_HALF_UP);
        log.info("calculated profitRate: {} saleAmount: {}, dailyIncome: {}.........", profitRate, saleAmount, dailyIncome);

        // 1. Save seller daily income
        //incomeRepo.save(new IncomeHistory(sellerId, dailyIncome, "DAILY", "SELF", LocalDate.now()));
        log.info("Saving direct income of {} for user: {}...........", dailyIncome, sellerId);
        incomeRepo.save(IncomeHistory.builder()
                .userId(sellerId)
                .amount(dailyIncome)
                .type(IncomeHistory.IncomeType.DAILY)
                .sourceUserId(sellerId)
                .sourceUserRank(sellerRank)
                .note("Self income")
                .build());
        log.info("Saved direct income of {} for user {}", dailyIncome, seller.getId());

        // 2. Fetch all uplines with rank info in a single query
        /*List<Object[]> uplineData = hierarchyRepo.findUplinesWithRank(sellerId, 3); // Depth ≤ 3

        for (Object[] row : uplineData) {
            Long uplineId = (Long) row[0];
            Integer depth = (Integer) row[1];
            Integer rankLevel = (Integer) row[2];

            Rank uplineRank = Rank.fromLevel(rankLevel);
            TeamIncomeConfig teamConfig = teamIncomeRepo.findById(uplineRank).orElse(null);
            if (teamConfig == null) continue;

            BigDecimal percentage = teamConfig.getIncomePercentages().getOrDefault(depth, BigDecimal.ZERO);
            BigDecimal teamIncome = dailyIncome.multiply(percentage).divide(BigDecimal.valueOf(100));

            if (teamIncome.compareTo(BigDecimal.ZERO) > 0) {
                //incomeRepo.save(new IncomeHistory(uplineId, teamIncome, "TEAM", "from: " + sellerId, LocalDate.now()));
                incomeRepo.save(IncomeHistory.builder()
                        .userId(uplineId)
                        .amount(teamIncome)
                        .type(IncomeType.TEAM)
                        .sourceUserId(sellerId)
                        .sourceUserRank(sellerRank)
                        .note("From user " + sellerId + ", depth " + depth)
                        .date(LocalDate.now())
                        .build());
            }
        }*/

        // 2. Load full hierarchy in one query
        log.info("Propagate team income for user: {}...........", sellerId);
        List<UserHierarchy> hierarchy = hierarchyRepo.findByDescendant(sellerId);
        Map<Long, Integer> uplinesWithDepth = hierarchy.stream()
                .filter(UserHierarchy::isActive)
                .collect(Collectors.toMap(UserHierarchy::getAncestor, UserHierarchy::getDepth));
        log.info("All uplines for user: {} is: {}", sellerId, uplinesWithDepth);

        // Load all upline users in a single query
        List<User> uplines = userRepository.findAllById(uplinesWithDepth.keySet());

        // Distribute team income
        log.info("Distribute team income for user: {}.............", sellerId);
        teamIncomeStrategy.distributeTeamIncome(sellerId, sellerRank, dailyIncome, uplines, uplinesWithDepth);
    }
}
