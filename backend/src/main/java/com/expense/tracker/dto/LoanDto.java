package com.expense.tracker.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class LoanDto {
    private Long id;
    private Long accountId;
    private BigDecimal totalAmount;
    private BigDecimal emiAmount;
    private BigDecimal paidAmount;
    private BigDecimal remainingAmount;
    private LocalDate startDate;
    private LocalDate endDate;

    // Explicit getters
    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getEmiAmount() {
        return emiAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}