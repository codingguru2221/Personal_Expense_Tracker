package com.expense.tracker.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BankDto {
    private Long id;
    private String bankName;
    private String bankCode;
    private String logoUrl;

    // Explicit getters
    public Long getId() {
        return id;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}