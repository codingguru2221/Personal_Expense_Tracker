package com.expense.tracker.service;

import com.expense.tracker.dto.AccountDto;
import com.expense.tracker.entity.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAccountsByUserId(Long userId);
    Account createAccount(AccountDto accountDto);
    Account linkAccount(Long accountId, boolean isLinked);
}