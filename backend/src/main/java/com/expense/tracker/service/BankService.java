package com.expense.tracker.service;

import com.expense.tracker.entity.Bank;

import java.util.List;

public interface BankService {
    List<Bank> getAllBanks();
    void seedBanks();
}