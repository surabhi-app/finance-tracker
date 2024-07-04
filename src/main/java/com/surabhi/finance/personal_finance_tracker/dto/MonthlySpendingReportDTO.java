package com.surabhi.finance.personal_finance_tracker.dto;

import java.math.BigDecimal;
import java.util.Map;

public class MonthlySpendingReportDTO {
    private Map<String, BigDecimal> categorizedSpending;
    private BigDecimal totalSpending;

    public MonthlySpendingReportDTO(Map<String, BigDecimal> categorizedSpending, BigDecimal totalSpending) {
        this.categorizedSpending = categorizedSpending;
        this.totalSpending = totalSpending;
    }

    public Map<String, BigDecimal> getCategorizedSpending() {
        return categorizedSpending;
    }

    public void setCategorizedSpending(Map<String, BigDecimal> categorizedSpending) {
        this.categorizedSpending = categorizedSpending;
    }

    public BigDecimal getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(BigDecimal totalSpending) {
        this.totalSpending = totalSpending;
    }
}

