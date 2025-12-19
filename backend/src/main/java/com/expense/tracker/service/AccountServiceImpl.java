package com.expense.tracker.service;

import com.expense.tracker.dto.AccountDto;
import com.expense.tracker.entity.Account;
import com.expense.tracker.entity.Bank;
import com.expense.tracker.entity.User;
import com.expense.tracker.repository.AccountRepository;
import com.expense.tracker.repository.BankRepository;
import com.expense.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    @Override
    public List<Account> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserIdWithBank(userId);
    }

    @Override
    public Account createAccount(AccountDto accountDto) {
        User user = userRepository.findById(accountDto.getUserId()).orElse(null);
        Bank bank = bankRepository.findById(accountDto.getBankId()).orElse(null);

        if (user == null || bank == null) {
            return null;
        }

        Account account = new Account();
        account.setUser(user);
        account.setBank(bank);
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setBalance(accountDto.getBalance());
        account.setLinked(accountDto.getIsLinked());

        return accountRepository.save(account);
    }

    @Override
    public Account linkAccount(Long accountId, boolean isLinked) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            account.setLinked(isLinked);
            return accountRepository.save(account);
        }
        return null;
    }
}