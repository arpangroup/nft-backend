package com.arpangroup.referral_service.income.repository;

import com.arpangroup.referral_service.income.entity.IncomeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeHistoryRepository extends JpaRepository<IncomeHistory, Long> {
}
