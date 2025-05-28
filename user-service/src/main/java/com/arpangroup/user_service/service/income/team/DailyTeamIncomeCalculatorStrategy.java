package com.arpangroup.user_service.service.income.team;

import com.arpangroup.user_service.entity.User;

import java.math.BigDecimal;

public interface DailyTeamIncomeCalculatorStrategy {
    BigDecimal calculateIncome(User upline, User downline, BigDecimal downlineIncome);
}
