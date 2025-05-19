package com.arpangroup.user_service.transaction;

import com.arpangroup.user_service.dto.DepositBalanceRequest;
import com.arpangroup.user_service.entity.Transaction;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findAllByUserId(userId);
    }

    public Transaction deposit(final long userId, final double amount, String remarks, String txnRefId, Double txnFee, String status) {
        validateUniqueTxnRefId(txnRefId);
        Transaction lastTxn = transactionRepository.findFirstByUserIdOrderByTxnDateDesc(userId);
        double currentBalance = lastTxn != null ? lastTxn.getBalance() : 0;

        Transaction transaction = new Transaction(userId, amount, TransactionType.DEPOSIT, currentBalance + amount);
        transaction.setSenderId(null);
        transaction.setRemarks(remarks);
        transaction.setTxnRefId(txnRefId);
        transaction.setStatus(status);
        transaction.setTxnFee(txnFee);

        transaction = transactionRepository.save(transaction);
        User user = userRepository.findById(userId).get();
        user.setReserveBalance(user.getReserveBalance() + amount);
        userRepository.save(user);

        return transaction;
    }

    public Transaction deposit(final long userId, final double amount, String remarks) {
        return this.deposit(userId, amount, remarks, null, null, null);
    }

    public Transaction withdraw(final long userId, final double amount, String remarks, Double txnFee, String status) {
        Transaction lastTxn = transactionRepository.findFirstByUserIdOrderByTxnDateDesc(userId);
        double currentBalance = lastTxn != null ? lastTxn.getBalance() : 0;
        if (currentBalance < amount) throw new TransactionException("Insufficient balance to withdraw");

        Transaction transaction = new Transaction(userId, amount, TransactionType.WITHDRAWAL, currentBalance - amount);
        transaction.setSenderId(null);
        transaction.setRemarks(remarks);
        transaction.setTxnRefId(null);
        transaction.setStatus(status);
        transaction.setTxnFee(txnFee);

        transaction = transactionRepository.save(transaction);
        User user = userRepository.findById(userId).get();
        user.setReserveBalance(user.getReserveBalance() - amount);
        userRepository.save(user);

        return transaction;
    }
    public Transaction withdraw(final long userId, final double amount) {
        return this.withdraw(userId, amount, TransactionRemarks.WITHDRAWAL, null, null);
    }

    public Transaction transfer(final long sender, final long receiver, final double amount, String remarks, Double txnFee, String status) {
        Transaction senderLastTxn = transactionRepository.findFirstBySenderIdOrderByTxnDateDesc(sender);
        double senderCurrentBalance = senderLastTxn != null ? senderLastTxn.getBalance() : 0;
        if (senderCurrentBalance < amount) throw new TransactionException("Insufficient balance to transfer");
        Transaction senderTxn = new Transaction(receiver, amount, TransactionType.TRANSFER, senderCurrentBalance - amount);
        senderTxn.setSenderId(sender);
        senderTxn.setRemarks(remarks);
        senderTxn.setTxnRefId(null);
        senderTxn.setStatus(status);
        senderTxn.setTxnFee(txnFee);
        transactionRepository.save(senderTxn);

        Transaction receiverLastTxn = transactionRepository.findFirstByUserIdOrderByTxnDateDesc(receiver);
        double receiverCurrentBalance = receiverLastTxn != null ? receiverLastTxn.getBalance() : 0;
        Transaction receiverTxn = new Transaction(receiver, amount, TransactionType.TRANSFER, receiverCurrentBalance + amount);
        receiverTxn.setSenderId(sender);
        receiverTxn.setRemarks(remarks);
        receiverTxn.setTxnRefId(null);
        receiverTxn.setStatus(status);
        receiverTxn.setTxnFee(txnFee);
        return transactionRepository.save(receiverTxn);
    }

    public Transaction transfer(final long sender, final long receiver, final double amount) {
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
