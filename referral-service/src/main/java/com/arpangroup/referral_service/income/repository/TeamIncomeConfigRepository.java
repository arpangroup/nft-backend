package com.arpangroup.referral_service.income.repository;

import com.arpangroup.referral_service.income.entity.TeamIncomeConfig;
import com.arpangroup.referral_service.rank.model.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamIncomeConfigRepository extends JpaRepository<TeamIncomeConfig, Rank> {
    Optional<TeamIncomeConfig> findByRank(Rank rank);
}
