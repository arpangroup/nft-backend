package com.arpangroup.referral_service.rank.repository;

import com.arpangroup.referral_service.rank.config.RankConfig;
import com.arpangroup.referral_service.rank.model.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankConfigRepository extends JpaRepository<RankConfig, Rank> {
}
