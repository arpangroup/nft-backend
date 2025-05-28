package com.arpangroup.product_service.repository;

import com.arpangroup.product_service.entity.UserCollection;
import com.arpangroup.product_service.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {
    List<UserCollection> findByUserIdAndStatus(Long userId, TransactionStatus status);

    Optional<UserCollection> findByUserIdAndProductIdAndStatus(Long userId, Long productId, TransactionStatus status);
}
