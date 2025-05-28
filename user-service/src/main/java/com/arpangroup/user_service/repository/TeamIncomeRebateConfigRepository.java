package com.arpangroup.user_service.repository;

import com.arpangroup.user_service.entity.config.DownlineTier;
import com.arpangroup.user_service.entity.config.TeamIncomeRebateConfig;
import com.arpangroup.user_service.entity.config.UplineLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamIncomeRebateConfigRepository extends JpaRepository<TeamIncomeRebateConfig, Long> {
    /**
     * Find the rebate percentage configuration based on the upline user's level
     * and the downline member's tier.
     *
     * @param uplineLevel  the level of the upline user (e.g. Level1, Level2)
     * @param downlineTier the tier of the downline user (e.g. LvA, LvB)
     * @return Optional configuration with percentage, or empty if not configured
     */
    Optional<TeamIncomeRebateConfig> findByUplineLevelAndDownlineTier(
            UplineLevel uplineLevel,
            DownlineTier downlineTier
    );
}
