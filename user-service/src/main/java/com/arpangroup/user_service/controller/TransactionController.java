package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.dto.DepositRequest;
import com.arpangroup.user_service.entity.Transaction;
import com.arpangroup.user_service.transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Page<Transaction>> transactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return ResponseEntity.ok(transactionService.getTransactions(pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<Transaction>> transactionsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "txnDate"));
        return ResponseEntity.ok(transactionService.getTransactionsByUserId(userId, pageable));
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@Valid @RequestBody DepositRequest request) {
        Transaction transaction = transactionService.deposit(request.getUserId(), request.getAmount(), request.getRemarks());
        return ResponseEntity.ok(transaction);
    }

}
