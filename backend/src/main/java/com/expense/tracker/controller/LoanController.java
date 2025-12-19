package com.expense.tracker.controller;

import com.expense.tracker.entity.Loan;
import com.expense.tracker.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "http://localhost:5173")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<List<Loan>> getLoansByUserId(@RequestParam Long userId) {
        List<Loan> loans = loanService.getLoansByUserId(userId);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
}