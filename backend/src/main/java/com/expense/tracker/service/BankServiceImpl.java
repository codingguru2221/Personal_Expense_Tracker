package com.expense.tracker.service;

import com.expense.tracker.entity.Bank;
import com.expense.tracker.repository.BankRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    private Bank createBank(String name, String code, String logoUrl) {
        Bank bank = new Bank();
        bank.setBankName(name);
        bank.setBankCode(code);
        bank.setLogoUrl(logoUrl);
        return bank;
    }

    @Autowired
    private BankRepository bankRepository;

    @PostConstruct
    public void init() {
        seedBanks();
    }

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public void seedBanks() {
        if (bankRepository.count() == 0) {
            List<Bank> banks = Arrays.asList(
                createBank("Chase Bank", "CHASUS33", "chase.png"),
                createBank("Bank of America", "BOFAUS6S", "boa.png"),
                createBank("Wells Fargo", "WFBIUS6S", "wellsfargo.png"),
                createBank("Citibank", "CITIUS33", "citi.png"),
                createBank("Capital One", "NATXUS33", "capitalone.png"),
                createBank("HSBC", "MRMDUS33", "hsbc.png"),
                createBank("PNC Bank", "PNCCUS33", "pnc.png"),
                createBank("TD Bank", "TDOMUS33", "tdbank.png"),
                createBank("US Bank", "USBKUS44", "usbank.png"),
                createBank("BB&T", "BRBTUS33", "bbt.png")
            );

            bankRepository.saveAll(banks);
        }
    }
}