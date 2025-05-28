//package com.arpangroup.user_service.service.income.team;
//
//import com.arpangroup.user_service.entity.User;
//import com.arpangroup.user_service.entity.config.TeamIncomeRebateConfig;
//import com.arpangroup.user_service.repository.TeamIncomeRebateConfigRepository;
//
//import java.math.BigDecimal;
//
//public class DatabaseBackedIncomeCalculator implements DailyTeamIncomeCalculatorStrategy {
//
//    private final TeamIncomeRebateConfigRepository rateRepo;
//
//    public DatabaseBackedIncomeCalculator(TeamIncomeRebateConfigRepository rateRepo) {
//        this.rateRepo = rateRepo;
//    }
//
//    @Override
//    public BigDecimal calculateIncome(User upline, User downline, BigDecimal downlineIncome) {
//        String level = upline.getLevel(); // e.g., "Level-3"
//        String tier = downline.getTier(); // e.g., "Lv.B"
//
//        BigDecimal percent = rateRepo.findByUplineLevelAndDownlineTier(level, tier)
//                .map(TeamIncomeRate::getPercentage)
//                .orElse(BigDecimal.ZERO);
//
//        return downlineIncome.multiply(percent);
//    }
//}
