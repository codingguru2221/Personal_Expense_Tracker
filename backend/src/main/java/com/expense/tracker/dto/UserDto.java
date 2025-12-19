package com.expense.tracker.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private String mobileNumber;
    private String city;
    private String country;
    private String username;
    private String password;
    private String appPin;
    private Boolean fingerprintEnabled;
    private BigDecimal monthlyBudget;
    private String currency;

    // Explicit getters
    public BigDecimal getMonthlyBudget() {
        return monthlyBudget;
    }

    public String getCurrency() {
        return currency;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAppPin() {
        return appPin;
    }

    public Boolean getFingerprintEnabled() {
        return fingerprintEnabled;
    }
}