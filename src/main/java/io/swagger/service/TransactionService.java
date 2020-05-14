package io.swagger.service;

import io.swagger.exceptions.CustomException;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
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
        List<Account.AccountTypeEnum> accountType  = new ArrayList<Account.AccountTypeEnum>();
        accountType.add(Account.AccountTypeEnum.CURRENT);
        account.setAccountType(accountType);

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
