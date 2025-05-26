package com.arpangroup.user_service.transaction;

import com.arpangroup.user_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByTxnRefId(String txnRefId);

    List<Transaction> findAllByUserId(Long userId);

    //@Query("SELECT t FROM Transaction t WHERE t.senderId = :userId OR t.userId = :userId ORDER BY t.txnDate DESC")
    //Transaction findLastTransactionByUserId(@Param("userId") Long userId);

    Transaction findFirstBySenderIdOrderByTxnDateDesc(Long senderId);

    Transaction findFirstByUserIdOrderByTxnDateDesc(Long userId);


    @Query(value = """
        SELECT EXISTS (
            SELECT 1 FROM transactions
            WHERE user_id = :userId AND txn_type = :txnType
            LIMIT 1
        )
    """, nativeQuery = true)
    boolean existsDepositTransaction(@Param("userId") Long userId, @Param("txnType") TransactionType txnType);
}
