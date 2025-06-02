package com.arpangroup.user_service.transaction;

import com.arpangroup.user_service.entity.Transaction;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Page<Transaction> getTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserIdOrderByTxnDateDesc(userId);
    }

    public Page<Transaction> getTransactionsByUserId(Long userId, Pageable pageable) {
        return transactionRepository.findByUserIdOrderByTxnDateDesc(userId, pageable);
    }

    public Boolean hasDepositTransaction(Long userId) {
        return transactionRepository.existsByUserIdAndTxnType(userId, TransactionType.DEPOSIT);
    }

    @Transactional
    public Transaction deposit(final long userId, final BigDecimal amount, String remarks, String txnRefId, Double txnFee, String status, String metaInfo) {
        log.info("Depositing Amount: {} for User ID: {}", amount, userId);
        validateUniqueTxnRefId(txnRefId);
        Transaction lastTxn = transactionRepository.findFirstByUserIdOrderByTxnDateDesc(userId);
        BigDecimal currentBalance = lastTxn != null ? lastTxn.getBalance() : BigDecimal.ZERO;

        Transaction transaction = new Transaction(userId, amount, TransactionType.DEPOSIT, currentBalance.add(amount));
        transaction.setSenderId(null);
        transaction.setRemarks(remarks);
        transaction.setTxnRefId(txnRefId);
        transaction.setStatus(status);
        transaction.setTxnFee(txnFee);
        transaction.setMetaInfo(metaInfo);

        log.info("Saving new Transaction record to DB: {}", transaction);
        transaction = transactionRepository.save(transaction);
        /*User user = userRepository.findById(userId).get();
        user.setWalletBalance(user.getWalletBalance().add(amount));
        userRepository.save(user);*/

        return transaction;
    }

    @Transactional
    public Transaction deposit(final long userId, final BigDecimal amount, String remarks) {
        return this.deposit(userId, amount, remarks, null, null, null, null);
    }

    @Transactional
    public Transaction deposit(final long userId, final BigDecimal amount, String remarks, String metaInfo) {
        return this.deposit(userId, amount, remarks, null, null, null, metaInfo);
    }

    public Transaction withdraw(final long userId, final BigDecimal amount, String remarks, Double txnFee, String status) {
        Transaction lastTxn = transactionRepository.findFirstByUserIdOrderByTxnDateDesc(userId);
        BigDecimal currentBalance = lastTxn != null ? lastTxn.getBalance() : BigDecimal.ZERO;
        if (currentBalance.compareTo(amount) < 0) throw new TransactionException("Insufficient balance to withdraw");

        Transaction transaction = new Transaction(userId, amount, TransactionType.WITHDRAWAL, currentBalance.subtract(amount));
        transaction.setSenderId(null);
        transaction.setRemarks(remarks);
        transaction.setTxnRefId(null);
        transaction.setStatus(status);
        transaction.setTxnFee(txnFee);

        transaction = transactionRepository.save(transaction);
        /*User user = userRepository.findById(userId).get();
        user.setWalletBalance(user.getWalletBalance().subtract(amount));
        userRepository.save(user);*/

        return transaction;
    }
    public Transaction withdraw(final long userId, final BigDecimal amount) {
        return this.withdraw(userId, amount, TransactionRemarks.WITHDRAWAL, null, null);
    }

    public Transaction transfer(final long sender, final long receiver, final BigDecimal amount, String remarks, Double txnFee, String status) {
        Transaction senderLastTxn = transactionRepository.findFirstBySenderIdOrderByTxnDateDesc(sender);
        BigDecimal senderCurrentBalance = senderLastTxn != null ? senderLastTxn.getBalance() : BigDecimal.ZERO;
        if (senderCurrentBalance.compareTo(amount) < 0) throw new TransactionException("Insufficient balance to transfer");
        Transaction senderTxn = new Transaction(receiver, amount, TransactionType.TRANSFER, senderCurrentBalance.subtract(amount));
        senderTxn.setSenderId(sender);
        senderTxn.setRemarks(remarks);
        senderTxn.setTxnRefId(null);
        senderTxn.setStatus(status);
        senderTxn.setTxnFee(txnFee);
        transactionRepository.save(senderTxn);

        Transaction receiverLastTxn = transactionRepository.findFirstByUserIdOrderByTxnDateDesc(receiver);
        BigDecimal receiverCurrentBalance = receiverLastTxn != null ? receiverLastTxn.getBalance() : BigDecimal.ZERO;;
        Transaction receiverTxn = new Transaction(receiver, amount, TransactionType.TRANSFER, receiverCurrentBalance.add(amount));
        receiverTxn.setSenderId(sender);
        receiverTxn.setRemarks(remarks);
        receiverTxn.setTxnRefId(null);
        receiverTxn.setStatus(status);
        receiverTxn.setTxnFee(txnFee);
        return transactionRepository.save(receiverTxn);
    }

    public Transaction transfer(final long sender, final long receiver, final BigDecimal amount) {
        return this.transfer(sender, receiver, amount, TransactionRemarks.TRANSFER, null, null);
    }

    private boolean validateUniqueTxnRefId(String txnRefId) throws TransactionException{
        if (txnRefId != null) {
            boolean exists = transactionRepository.existsByTxnRefId(txnRefId);
            if (exists) {
                throw new TransactionException("Transaction ReferenceID must be unique");
            }
        }
        return true;
    }
}
