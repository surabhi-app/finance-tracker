package com.surabhi.finance.personal_finance_tracker.service;

import com.surabhi.finance.personal_finance_tracker.dto.MonthlySpendingReportDTO;
import com.surabhi.finance.personal_finance_tracker.dto.TransactionDTO;
import com.surabhi.finance.personal_finance_tracker.model.Account;
import com.surabhi.finance.personal_finance_tracker.model.Investment;
import com.surabhi.finance.personal_finance_tracker.model.Transaction;
import com.surabhi.finance.personal_finance_tracker.repository.AccountRepository;
import com.surabhi.finance.personal_finance_tracker.repository.InvestmentRepository;
import com.surabhi.finance.personal_finance_tracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FinanceService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private InvestmentRepository investmentRepository;

    public Transaction createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setDate(transactionDTO.getDate());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setCategory(transactionDTO.getCategory());

        // Fetch the account based on accountId and set it
        Account account = accountRepository.findById(transactionDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByAccount(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    // Transactions
    public void saveTransactions(List<Transaction> transactions) {
        transactionRepository.saveAll(transactions);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setDate(transactionDetails.getDate());
        transaction.setAmount(transactionDetails.getAmount());
        transaction.setCategory(transactionDetails.getCategory());
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    // Accounts
    public void saveAccounts(List<Account> accounts) {
        accountRepository.saveAll(accounts);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account updateAccount(Long id, Account accountDetails) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setAccountName(accountDetails.getAccountName());
        account.setBalance(accountDetails.getBalance());
        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    // Investments
    public void saveInvestments(List<Investment> investments) {
        investmentRepository.saveAll(investments);
    }

    public List<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }

    public Investment updateInvestment(Long id, Investment investmentDetails) {
        Investment investment = investmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Investment not found"));
        investment.setType(investmentDetails.getType());
        investment.setValue(investmentDetails.getValue());
        return investmentRepository.save(investment);
    }

    public void deleteInvestment(Long id) {
        investmentRepository.deleteById(id);
    }

    // Generate monthly spending report
    public MonthlySpendingReportDTO getMonthlySpendingReport(Long accountId, YearMonth month) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);

        Map<String, BigDecimal> categorizedSpending = transactions.stream()
                .filter(t -> YearMonth.from(t.getDate()).equals(month))
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
                ));

        BigDecimal totalSpending = categorizedSpending.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new MonthlySpendingReportDTO(categorizedSpending, totalSpending);
    }

    // Get total balance across all accounts
    public BigDecimal getTotalBalance() {
        return accountRepository.findAll().stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}