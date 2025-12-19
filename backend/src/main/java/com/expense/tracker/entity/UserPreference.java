package com.expense.tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "user_preferences")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "monthly_budget")
    private BigDecimal monthlyBudget;

    private String currency; // USD, INR, EUR, GBP

    @Column(name = "expense_categories", columnDefinition = "TEXT")
    private String expenseCategories; // JSON string

    // Getters for Lombok compatibility
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getMonthlyBudget() {
        return monthlyBudget;
    }

    public String getCurrency() {
        return currency;
    }

    public String getExpenseCategories() {
        return expenseCategories;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMonthlyBudget(BigDecimal monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setExpenseCategories(String expenseCategories) {
        this.expenseCategories = expenseCategories;
    }
}