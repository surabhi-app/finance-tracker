package com.surabhi.finance.personal_finance_tracker.repository;

import com.surabhi.finance.personal_finance_tracker.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByType(String type);
}
