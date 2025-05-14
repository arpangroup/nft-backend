package com.arpangroup.user_service.repository;

import com.arpangroup.user_service.entity.Referral;
import com.arpangroup.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {
    List<Referral> findByReferrerId(Long referrerId);
    List<Referral> findByReferredUserId(Long referredUserId);
}
