package com.expense.tracker.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class AccountDto {
    private Long id;
    private Long userId;
    private Long bankId;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private Boolean isLinked;
    private BankDto bank;

    // Explicit getters
    public Long getUserId() {
        return userId;
    }

    public Long getBankId() {
        return bankId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Boolean getIsLinked() {
        return isLinked;
    }
}