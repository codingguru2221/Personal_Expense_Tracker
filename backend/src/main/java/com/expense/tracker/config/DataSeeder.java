package com.expense.tracker.config;

import com.expense.tracker.entity.*;
import com.expense.tracker.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Bank createBank(String name, String code, String logoUrl) {
        Bank bank = new Bank();
        bank.setBankName(name);
        bank.setBankCode(code);
        bank.setLogoUrl(logoUrl);
        return bank;
    }

    @PostConstruct
    public void seedData() {
        // Check if data already exists
        if (userRepository.count() > 0) {
            return; // Data already seeded
        }

        // Create a sample user
        User user = new User();
        user.setFullName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setMobileNumber("+1234567890");
        user.setCity("New York");
        user.setCountry("USA");
        user.setUsername("johndoe");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setAppPin(passwordEncoder.encode("1234"));
        user.setFingerprintEnabled(true);

        user = userRepository.save(user);

        // Create user preferences
        UserPreference userPreference = new UserPreference();
        userPreference.setUser(user);
        userPreference.setMonthlyBudget(new BigDecimal("5000"));
        userPreference.setCurrency("USD");
        userPreference.setExpenseCategories("[\"Food\", \"Rent\", \"Shopping\", \"Utilities\"]");

        userPreferenceRepository.save(userPreference);

        // Create sample banks if they don't exist
        if (bankRepository.count() == 0) {
            List<Bank> banks = Arrays.asList(
                    createBank("Chase Bank", "CHASUS33", "chase.png"),
                    createBank("Bank of America", "BOFAUS6S", "boa.png"),
                    createBank("Wells Fargo", "WFBIUS6S", "wellsfargo.png"),
                    createBank("Citibank", "CITIUS33", "citi.png")
            );

            bankRepository.saveAll(banks);
        }

        List<Bank> banks = bankRepository.findAll();

        // Create sample accounts
        Account checkingAccount = new Account();
        checkingAccount.setUser(user);
        checkingAccount.setBank(banks.get(0)); // Chase Bank
        checkingAccount.setAccountNumber("****4589");
        checkingAccount.setAccountType("SAVINGS");
        checkingAccount.setBalance(new BigDecimal("5000"));
        checkingAccount.setLinked(true);

        Account savingsAccount = new Account();
        savingsAccount.setUser(user);
        savingsAccount.setBank(banks.get(1)); // Bank of America
        savingsAccount.setAccountNumber("****1290");
        savingsAccount.setAccountType("CURRENT");
        savingsAccount.setBalance(new BigDecimal("10000"));
        savingsAccount.setLinked(true);

        Account creditCardAccount = new Account();
        creditCardAccount.setUser(user);
        creditCardAccount.setBank(banks.get(2)); // Wells Fargo
        creditCardAccount.setAccountNumber("****8821");
        creditCardAccount.setAccountType("LOAN");
        creditCardAccount.setBalance(new BigDecimal("2000"));
        creditCardAccount.setLinked(false);

        List<Account> accounts = accountRepository.saveAll(Arrays.asList(checkingAccount, savingsAccount, creditCardAccount));

        // Create sample transactions
        Transaction transaction1 = new Transaction();
        transaction1.setAccount(checkingAccount);
        transaction1.setTransactionType("DEBIT");
        transaction1.setCategory("Food");
        transaction1.setAmount(new BigDecimal("25.50"));
        transaction1.setDescription("Grocery shopping");
        transaction1.setTransactionDate(LocalDateTime.now().minusDays(1));

        Transaction transaction2 = new Transaction();
        transaction2.setAccount(savingsAccount);
        transaction2.setTransactionType("CREDIT");
        transaction2.setCategory("Salary");
        transaction2.setAmount(new BigDecimal("3000"));
        transaction2.setDescription("Monthly salary");
        transaction2.setTransactionDate(LocalDateTime.now().minusDays(5));

        Transaction transaction3 = new Transaction();
        transaction3.setAccount(checkingAccount);
        transaction3.setTransactionType("DEBIT");
        transaction3.setCategory("Shopping");
        transaction3.setAmount(new BigDecimal("150.00"));
        transaction3.setDescription("Clothing purchase");
        transaction3.setTransactionDate(LocalDateTime.now().minusDays(2));

        transactionRepository.saveAll(Arrays.asList(transaction1, transaction2, transaction3));

        // Create a sample loan
        Loan loan = new Loan();
        loan.setAccount(creditCardAccount);
        loan.setTotalAmount(new BigDecimal("10000"));
        loan.setEmiAmount(new BigDecimal("500"));
        loan.setPaidAmount(new BigDecimal("2000"));
        loan.setRemainingAmount(new BigDecimal("8000"));
        loan.setStartDate(LocalDate.now().minusMonths(6));
        loan.setEndDate(LocalDate.now().plusMonths(18));

        loanRepository.save(loan);
    }
}