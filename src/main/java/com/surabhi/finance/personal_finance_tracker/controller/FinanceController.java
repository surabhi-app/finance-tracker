package com.surabhi.finance.personal_finance_tracker.controller;

import com.surabhi.finance.personal_finance_tracker.dto.MonthlySpendingReportDTO;
import com.surabhi.finance.personal_finance_tracker.dto.TransactionDTO;
import com.surabhi.finance.personal_finance_tracker.model.Account;
import com.surabhi.finance.personal_finance_tracker.model.Investment;
import com.surabhi.finance.personal_finance_tracker.model.Transaction;
import com.surabhi.finance.personal_finance_tracker.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/finances")
public class FinanceController {
    @Autowired
    private FinanceService financeService;

    // Transactions
    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            Transaction transaction = financeService.createTransaction(transactionDTO);
            return ResponseEntity.ok().body("Transaction created successfully with ID: " + transaction.getId());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return financeService.getAllTransactions();
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        return ResponseEntity.ok(financeService.updateTransaction(id, transaction));
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        financeService.deleteTransaction(id);
        return ResponseEntity.ok("Transaction deleted successfully");
    }

    // Accounts
    @PostMapping("/accounts")
    public ResponseEntity<?> addAccounts(@RequestBody List<Account> accounts) {
        financeService.saveAccounts(accounts);
        return ResponseEntity.ok("Accounts added successfully");
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return financeService.getAllAccounts();
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        return ResponseEntity.ok(financeService.updateAccount(id, account));
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        financeService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully");
    }

    // Investments
    @PostMapping("/investments")
    public ResponseEntity<?> addInvestments(@RequestBody List<Investment> investments) {
        financeService.saveInvestments(investments);
        return ResponseEntity.ok("Investments added successfully");
    }

    @GetMapping("/investments")
    public List<Investment> getAllInvestments() {
        return financeService.getAllInvestments();
    }

    @PutMapping("/investments/{id}")
    public ResponseEntity<Investment> updateInvestment(@PathVariable Long id, @RequestBody Investment investment) {
        return ResponseEntity.ok(financeService.updateInvestment(id, investment));
    }

    @DeleteMapping("/investments/{id}")
    public ResponseEntity<?> deleteInvestment(@PathVariable Long id) {
        financeService.deleteInvestment(id);
        return ResponseEntity.ok("Investment deleted successfully");
    }

    // Reports
    @GetMapping("/reports/monthly-spending/{accountId}")
    public ResponseEntity<MonthlySpendingReportDTO> getMonthlySpendingReport(@PathVariable Long accountId, @RequestParam String month) {
        YearMonth yearMonth = YearMonth.parse(month);
        MonthlySpendingReportDTO report = financeService.getMonthlySpendingReport(accountId, yearMonth);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/total-balance")
    public ResponseEntity<BigDecimal> getTotalBalance() {
        BigDecimal totalBalance = financeService.getTotalBalance();
        return ResponseEntity.ok(totalBalance);
    }
}

