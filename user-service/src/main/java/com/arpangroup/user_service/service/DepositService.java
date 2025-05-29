package com.arpangroup.user_service.service;

import com.arpangroup.nft_common.event.FirstDepositEvent;
import com.arpangroup.nft_common.event.UserRegisteredEvent;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.exception.IdNotFoundException;
import com.arpangroup.user_service.repository.UserRepository;
import com.arpangroup.user_service.transaction.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepositService {
    private final TransactionService transactionService;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public User deposit(long userId, BigDecimal amount, String remarks) {
        log.info("Deposit for userId: {}, amount: {}, remarks: {}", userId, amount, remarks);
        boolean hasAnyPreviousDeposit = hasDeposit(userId); // require to check if this txn is first deposit or not

        // add the record to transaction
        transactionService.deposit(userId, amount, remarks);

        // update user's wallet balance
        User user = userRepository.findById(userId).orElseThrow(() -> new IdNotFoundException("invalid userId: " + userId));
        user.setWalletBalance(user.getWalletBalance().add(amount));
        userRepository.save(user);

        if (!hasAnyPreviousDeposit) { // This is the first Deposit Transaction
            log.info("publishing FirstDepositEvent.....");
            publisher.publishEvent(new FirstDepositEvent(userId, user.getReferrer().getId(), amount));
        }

        return user;
    }

    public boolean hasDeposit(Long userId) {
        return transactionService.hasDepositTransaction(userId);
    }
}
