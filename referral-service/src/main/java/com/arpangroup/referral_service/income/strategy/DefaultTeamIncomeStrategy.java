package com.arpangroup.referral_service.income.strategy;

import com.arpangroup.referral_service.income.dto.UplineIncomeLog;
import com.arpangroup.referral_service.income.entity.IncomeHistory;
import com.arpangroup.referral_service.income.repository.IncomeHistoryRepository;
import com.arpangroup.referral_service.income.service.TeamCommissionService;
import com.arpangroup.referral_service.rank.model.Rank;
import com.arpangroup.user_service.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultTeamIncomeStrategy implements TeamIncomeStrategy {
    private final IncomeHistoryRepository incomeHistoryRepo;
    private final TeamCommissionService teamCommissionService;

    @Override
    public List<UplineIncomeLog> distributeTeamIncome(Long sourceUserId, Rank sourceUserRank, BigDecimal baseIncome, List<User> uplines, Map<Long, Integer> uplineDepthMap) {
        log.info("Inside distributeTeamIncome for sourceUserId: {}, sourceUserRank: {}, baseIncome: {}..................", sourceUserId, sourceUserRank, baseIncome);
        log.info("All uplines: {}", uplineDepthMap);
        List<UplineIncomeLog> incomeLogs = new ArrayList<>();

        for (User upline : uplines) {
            Rank uplineUserRank = Rank.fromValue(upline.getRank());
            //RankConfig rankConfig = rankConfigRepository.findById(uplineUserRank).orElseThrow(() -> new IllegalStateException("Rank config not found: " + uplineUserRank));
            Integer depth = uplineDepthMap.get(upline.getId());
            if (depth == null) {
                log.warn("Skipping upline {} — depth not found", upline.getId());
                continue;
            }

            BigDecimal percentage = teamCommissionService.getTeamCommissionPercentage(uplineUserRank, depth);
            log.info("TeamIncome Percentage for uplineUser: {} is: {} for rank: {} and depth: {}", upline.getId(), percentage, uplineUserRank, depth);
            if (percentage.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal teamIncome = baseIncome.multiply(percentage);
                //incomeHistoryRepository.save(new IncomeHistory(upline.getId(), teamIncome, IncomeHistory.IncomeType.TEAM));
                log.info("Saving team income of {} for user {}................", teamIncome, upline.getId());
                incomeHistoryRepo.save(IncomeHistory.builder()
                        .userId(upline.getId())
                        .amount(teamIncome)
                        .type(IncomeHistory.IncomeType.TEAM)
                        .sourceUserId(sourceUserId)
                        .sourceUserRank(sourceUserRank)
                        .note("Team income")
                        .build());
                log.info("Saved team income of {} for user {}", teamIncome, upline.getId());
                incomeLogs.add(new UplineIncomeLog(upline.getId(), uplineUserRank, depth, percentage, teamIncome));
            }
        }
        return incomeLogs;
    }
}
