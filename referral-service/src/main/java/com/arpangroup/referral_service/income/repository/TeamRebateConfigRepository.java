package com.arpangroup.referral_service.income.repository;

import com.arpangroup.referral_service.income.entity.TeamRebateConfig;
import com.arpangroup.referral_service.rank.model.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRebateConfigRepository extends JpaRepository<TeamRebateConfig, Rank> {
    Optional<TeamRebateConfig> findByRank(Rank rank);
}
