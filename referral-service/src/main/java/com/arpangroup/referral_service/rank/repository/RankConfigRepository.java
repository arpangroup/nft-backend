package com.arpangroup.referral_service.rank.repository;

import com.arpangroup.referral_service.rank.config.RankConfig;
import com.arpangroup.referral_service.rank.model.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RankConfigRepository extends JpaRepository<RankConfig, Rank> {
    Optional<RankConfig> findByRank(Rank rank);
}
