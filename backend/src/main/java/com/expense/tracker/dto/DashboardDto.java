package com.expense.tracker.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Getter
@Setter
public class DashboardDto {
    private BigDecimal totalBalance;
    private BigDecimal monthlySpend;
    private BigDecimal savings;
    private Map<String, BigDecimal> expenseBreakdownByCategory;
    private Map<String, BigDecimal> sevenDaySpendingAnalytics;

    // Explicit setters
    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public void setMonthlySpend(BigDecimal monthlySpend) {
        this.monthlySpend = monthlySpend;
    }

    public void setSavings(BigDecimal savings) {
        this.savings = savings;
    }

    public void setExpenseBreakdownByCategory(Map<String, BigDecimal> expenseBreakdownByCategory) {
        this.expenseBreakdownByCategory = expenseBreakdownByCategory;
    }

    public void setSevenDaySpendingAnalytics(Map<String, BigDecimal> sevenDaySpendingAnalytics) {
        this.sevenDaySpendingAnalytics = sevenDaySpendingAnalytics;
    }
}