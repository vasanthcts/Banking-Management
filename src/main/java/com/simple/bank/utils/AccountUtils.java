package com.simple.bank.utils;

import com.simple.bank.bo.AccountRequest;
import com.simple.bank.entity.Account;
import com.simple.bank.entity.Loan;
import com.simple.bank.repo.AccountRepository;
import com.simple.bank.repo.LoanRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.simple.bank.utils.Constants.*;

import java.util.List;
import java.util.Optional;

@Service
public class AccountUtils {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Value("${savings.account.transaction.fee}")
    private float savingsAccTransactionFee;

    @Value("${current.account.transaction.fee}")
    private float currentAccTransactionFee;

    @Value("${loan.savings.interest.rate}")
    private float savingsLoanInterestRate;

    @Value("${loan.current.interest.rate}")
    private float currentLoanInterestRate;

    private static final Logger logger = LogManager.getLogger(AccountUtils.class);

    public Account retrieveAccount(Long accNo) {
        logger.info("Searching for account");
        Optional<Account> account = accountRepository.findById(accNo);
        if(!account.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "account not found");
        logger.info("Account found");
        return account.get();
    }

    public void validateAccount (Long accNo) {
        Account account = retrieveAccount(accNo);
        logger.info("Account is disabled: {}", account.isDisabled());
        if (account.isDisabled()) {
            throw new ResponseStatusException
                    (HttpStatus.BAD_REQUEST, "Account disabled!");
        }
    }

    public Account disableAccount(Long accNo) {
        logger.info("Checking if account has existing loan with bank...");
        List<Loan> loanList = loanRepository.findByAccNoIsActive(accNo);
        if(!loanList.isEmpty()) {
            logger.error("Cannot disable account with unpaid loan!");
            throw new ResponseStatusException
                    (HttpStatus.BAD_REQUEST, "Account has outstanding loan!");
        }

        logger.info("Disabling account");
        Account account = retrieveAccount(accNo);
        account.setDisabled(true);
        account.setBalance(0);
        accountRepository.save(account);
        logger.info("Account disabled");
        return account;
    }

    public float getTransactionFee(String accType) {
        if(accType.compareToIgnoreCase(Constants.CURRENT) == 0)
            return currentAccTransactionFee;
        return savingsAccTransactionFee;
    }

    public float getLoanInterestRate(String accType) {
        if(accType.compareToIgnoreCase(Constants.CURRENT) == 0)
            return currentLoanInterestRate;
        return savingsLoanInterestRate;
    }

    public Account createAccount(AccountRequest accountRequest) {
        logger.info("Opening new account");
        float transactionFee = getTransactionFee(accountRequest.getAccountType());
        Account account = new Account (accountRequest.getName(),
                accountRequest.getDob(), accountRequest.getAccountType().toUpperCase(),
                transactionFee, accountRequest.getInitialDeposit());
        logger.info("Created new account");

        accountRepository.save(account);
        logger.info("Saved account to database");
        return account;
    }

}
