package com.expense.tracker.service;

import com.expense.tracker.entity.Loan;

import java.util.List;

public interface LoanService {
    List<Loan> getLoansByUserId(Long userId);
}