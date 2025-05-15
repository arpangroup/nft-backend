package com.arpangroup.user_service.transaction;

import com.arpangroup.user_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByTxnRefId(String txnRefId);

    @Query("SELECT t FROM Transaction t WHERE t.senderId = :userId OR t.userId = :userId ORDER BY t.txnDate DESC")
    Transaction findLastTransactionByUserId(@Param("userId") Long userId);
}
