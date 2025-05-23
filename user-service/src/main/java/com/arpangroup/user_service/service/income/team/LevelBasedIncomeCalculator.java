//package com.arpangroup.user_service.service.income.team;
//
//import com.arpangroup.user_service.entity.User;
//
//import java.math.BigDecimal;
//import java.util.Map;
//
//public class LevelBasedIncomeCalculator implements DailyTeamIncomeCalculatorStrategy {
//    private static final Map<String, Map<String, BigDecimal>> incomePercentageMap = Map.of(
//            "Level-2", Map.of(
//                    "Lv.A", new BigDecimal("0.12"),
//                    "Lv.B", new BigDecimal("0.05"),
//                    "Lv.C", new BigDecimal("0.02")
//            ),
//            "Level-3", Map.of(
//                    "Lv.A", new BigDecimal("0.13"),
//                    "Lv.B", new BigDecimal("0.06"),
//                    "Lv.C", new BigDecimal("0.03")
//            ),
//            "Level-4", Map.of(
//                    "Lv.A", new BigDecimal("0.15"),
//                    "Lv.B", new BigDecimal("0.07"),
//                    "Lv.C", new BigDecimal("0.03")
//            ),
//            "Level-5", Map.of(
//                    "Lv.A", new BigDecimal("0.16"),
//                    "Lv.B", new BigDecimal("0.08"),
//                    "Lv.C", new BigDecimal("0.07")
//            )
//    );
//
//    @Override
//    public BigDecimal calculateIncome(User upline, User downline, BigDecimal downlineIncome) {
//        String uplineLevel = upline.getLevel(); // e.g., "Level-2"
//        String downlineTier = determineTier(downline); // e.g., "Lv.A"
//
//        Map<String, BigDecimal> tierPercentages = incomePercentageMap.getOrDefault(uplineLevel, Map.of());
//        BigDecimal percentage = tierPercentages.getOrDefault(downlineTier, BigDecimal.ZERO);
//
//        return downlineIncome.multiply(percentage);
//    }
//
//    private String determineTier(User downline) {
//        // Replace with real tier logic, example:
//        return downline.getTier(); // returns "Lv.A", "Lv.B", or "Lv.C"
//    }
//}
