package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.MachineTransfer;
import io.swagger.model.Transaction;
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

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    public List<Transaction> getAllTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    public List<Transaction> getAllTransactionsOfUser(Integer userId/*Pageable pageable*/) {
        Account account =  new Account();
        account.setIban("NL12INHO1234567890");
        account.setBalance(1000.00f);
        account.setOwner(userId);
        account.setTransactionAmountLimit(new BigDecimal(10));
        account.setTransactionDayLimit(10);
        account.setAccountType(Account.AccountTypeEnum.CURRENT);

        return transactionRepository.findByIban(account.getIban());
    }


    public Integer createMachineTransfer(int userId, double amount, String transfer_type)
    {
        //withdraw remove from bank account and banks own
        //deposit add to bank account and banks own
        transactionRepository.save(new Transaction(transfer_type, LocalDateTime.now(), "NL13INHO1234567890", "NL13INHO1234567890", amount, userId));
        return 201;
    }

    public Transaction createTransactionForUser(Transaction transaction) {
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setTransactionType(Transaction.TransactionTypeEnum.TRANSACTION);
        transaction.setUserPerforming(100053);
        return transactionRepository.save(transaction);
    }
}
