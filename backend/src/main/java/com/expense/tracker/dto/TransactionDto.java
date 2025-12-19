package com.expense.tracker.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class TransactionDto {
    private Long id;
    private Long accountId;
    private String transactionType;
    private String category;
    private BigDecimal amount;
    private String description;
    private LocalDateTime transactionDate;

    // Explicit getters
    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}