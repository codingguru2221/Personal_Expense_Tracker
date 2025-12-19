package com.expense.tracker.service;

import com.expense.tracker.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactionsByUserId(Long userId);
    Transaction createTransaction(Transaction transaction);
}