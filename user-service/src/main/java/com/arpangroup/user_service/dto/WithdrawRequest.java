package com.arpangroup.user_service.dto;

import com.arpangroup.user_service.transaction.TransactionType;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class WithdrawRequest {
    @NotNull(message = "User ID cannot be null")
    @Min(value = 1, message = "User ID must be greater than 0")
    private Long userId;
    @Min(value = 1, message = "Amount must be greater than 0")
    private BigDecimal amount;
    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType = TransactionType.DEPOSIT;
    private String remarks;

    @JsonSetter("transactionType")
    public void setTransactionType(String type) {
        this.transactionType = TransactionType.valueOf(type.toUpperCase());
    }
}
