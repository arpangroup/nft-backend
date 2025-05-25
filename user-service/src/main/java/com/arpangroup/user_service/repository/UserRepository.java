package com.arpangroup.user_service.repository;

import com.arpangroup.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByReferralCode(String referralCode);
    List<User> findAllByIdIn(List<Long> ids);
}
