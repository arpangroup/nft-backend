package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.dto.DepositBalanceRequest;
import com.arpangroup.user_service.entity.Transaction;
import com.arpangroup.user_service.entity.User;
import com.arpangroup.user_service.transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> transactions() {
        return ResponseEntity.ok(transactionService.getTransactions());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Transaction>> transactionsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserId(userId));
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@Valid @RequestBody DepositBalanceRequest request) {
        Transaction transaction = transactionService.deposit(request.getUserId(), request.getAmount(), request.getRemarks());
        return ResponseEntity.ok(transaction);
    }

}
