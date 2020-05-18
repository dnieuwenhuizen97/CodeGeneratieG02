package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.MachineTransfer;
import io.swagger.model.Transaction;
import io.swagger.repository.AccountRepository;
import org.springframework.stereotype.Service;
import io.swagger.repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    TransactionRepository transactionRepository;
    AccountRepository accountRepository;
    private Account bankOwnAccount;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }
    public List<Transaction> getAllTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    public List<Transaction> getAllTransactionsOfUser(Integer userId/*Pageable pageable*/) {

        //account ophalen by userid

        Account account =  new Account();
        account.setIban("NL12INHO1234567890");
        account.setBalance(1000.00f);
        account.setOwner(userId);
        account.setTransactionAmountLimit(new BigDecimal(10));
        account.setTransactionDayLimit(10);
        account.setAccountType(Account.AccountTypeEnum.CURRENT);

        return transactionRepository.findByIban(account.getIban());
    }

    public Transaction createMachineTransfer(int userId, MachineTransfer machineTransfer)
    {
        this.bankOwnAccount =  accountRepository.findById("NL01INHO0000000001").get();
        List<Account> userAccounts = accountRepository.findAccountByOwner(userId);

        for (Account userAccount: userAccounts) {
            if(userAccount.getAccountType() == Account.AccountTypeEnum.CURRENT)
            {
                switch (machineTransfer.getTransferType())
                {
                    //add amount to account and bank own account;
                    case DEPOSIT:
                        userAccount.setBalance(userAccount.getBalance() + machineTransfer.getAmount());
                        bankOwnAccount.setBalance(bankOwnAccount.getBalance() + machineTransfer.getAmount());
                        break;
                    //remove amount from user account and bank account
                    case WITHDRAW:
                        if(userAccount.getBalance() < machineTransfer.getAmount())
                            return null;
                        userAccount.setBalance(userAccount.getBalance() - machineTransfer.getAmount());
                        bankOwnAccount.setBalance(bankOwnAccount.getBalance() - machineTransfer.getAmount());
                        break;
                }
            }
        }

        Transaction machineTransaction = new Transaction(machineTransfer.getTransferType().toString(), LocalDateTime.now(), "NL13INHO1234567890", "NL13INHO1234567890", machineTransfer.getAmount(), userId);
        transactionRepository.save(machineTransaction);
        return machineTransaction;
    }

    public Transaction createTransactionForUser(Transaction transaction) {

        //to do geld ook echt overschrijven

        transaction.setTimestamp(LocalDateTime.now());
        transaction.setTransactionType(Transaction.TransactionTypeEnum.TRANSACTION);
        transaction.setUserPerforming(100053);
        return transactionRepository.save(transaction);
    }
}
