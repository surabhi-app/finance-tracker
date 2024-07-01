package com.surabhi.finance.personal_finance_tracker.repository;

import com.surabhi.finance.personal_finance_tracker.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountName(String accountName);
}
