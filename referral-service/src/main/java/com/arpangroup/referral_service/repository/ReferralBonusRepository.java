package com.arpangroup.referral_service.repository;

import com.arpangroup.referral_service.domain.entity.ReferralBonus;
import com.arpangroup.referral_service.domain.enums.BonusStatus;
import com.arpangroup.nft_common.enums.ReferralBonusTriggerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralBonusRepository extends JpaRepository<ReferralBonus, Long> {
    List<ReferralBonus> findByRefereeIdAndTriggerTypeAndEvaluatedFalse(Long refereeId, ReferralBonusTriggerType referralBonusTriggerType);
    List<ReferralBonus> findByStatus(BonusStatus status);

    // Optional alias:
    @Query("SELECT rb FROM ReferralBonus rb WHERE rb.status = 'PENDING'")
    List<ReferralBonus> findAllPending();
}
