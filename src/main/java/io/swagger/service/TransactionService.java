package io.swagger.service;

import io.swagger.model.Transaction;
import org.springframework.stereotype.Service;
import io.swagger.repository.TransactionRepository;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    public List<Transaction> getAllTransactions()
    {
        return (List<Transaction>)transactionRepository.findAll();
    }

    public Integer createMachineTransfer(int userId, double amount, String transfer_type)
    {
        //withdraw remove from bank account and banks own

        //deposit add to bank account and banks own

        transactionRepository.save(new Transaction(transfer_type, LocalDateTime.now(), "NL13INHO1234567890", "NL13INHO1234567890", amount, userId));
        return 201;
    }
}
