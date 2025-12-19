package com.expense.tracker.service;

import com.expense.tracker.dto.DashboardDto;
import com.expense.tracker.entity.Account;
import com.expense.tracker.entity.Transaction;
import com.expense.tracker.repository.AccountRepository;
import com.expense.tracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public DashboardDto getDashboardData(Long userId) {
        DashboardDto dashboardDto = new DashboardDto();

        // Get all accounts for the user
        List<Account> accounts = accountRepository.findByUserId(userId);

        // Calculate total balance
        BigDecimal totalBalance = accounts.stream()
                .map(account -> account.getBalance())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dashboardDto.setTotalBalance(totalBalance);

        // Get all transactions for the user
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        // Calculate monthly spend (current month)
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        BigDecimal monthlySpend = transactions.stream()
                .filter(t -> t.getTransactionDate().isAfter(startOfMonth))
                .filter(t -> "DEBIT".equals(t.getTransactionType()))
                .map(transaction -> transaction.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dashboardDto.setMonthlySpend(monthlySpend);

        // Calculate savings (assuming it's the difference between credits and debits)
        BigDecimal totalCredits = transactions.stream()
                .filter(t -> "CREDIT".equals(t.getTransactionType()))
                .map(transaction -> transaction.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalDebits = transactions.stream()
                .filter(t -> "DEBIT".equals(t.getTransactionType()))
                .map(transaction -> transaction.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dashboardDto.setSavings(totalCredits.subtract(totalDebits));

        // Expense breakdown by category
        Map<String, BigDecimal> expenseBreakdown = transactions.stream()
                .filter(t -> "DEBIT".equals(t.getTransactionType()))
                .collect(Collectors.groupingBy(
                        transaction -> transaction.getCategory(),
                        Collectors.reducing(BigDecimal.ZERO, transaction -> transaction.getAmount(), BigDecimal::add)
                ));
        dashboardDto.setExpenseBreakdownByCategory(expenseBreakdown);

        // 7-day spending analytics
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        Map<String, BigDecimal> sevenDaySpending = transactions.stream()
                .filter(t -> t.getTransactionDate().isAfter(sevenDaysAgo))
                .filter(t -> "DEBIT".equals(t.getTransactionType()))
                .collect(Collectors.groupingBy(
                        t -> t.getTransactionDate().toLocalDate().toString(),
                        Collectors.reducing(BigDecimal.ZERO, transaction -> transaction.getAmount(), BigDecimal::add)
                ));
        dashboardDto.setSevenDaySpendingAnalytics(sevenDaySpending);

        return dashboardDto;
    }
}