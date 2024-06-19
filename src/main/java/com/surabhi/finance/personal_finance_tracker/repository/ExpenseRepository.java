package com.surabhi.finance.personal_finance_tracker.repository;

import com.surabhi.finance.personal_finance_tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
