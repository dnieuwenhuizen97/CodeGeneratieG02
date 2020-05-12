package io.swagger.service;

import io.swagger.model.MachineTransfer;
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

    public Transaction createMachineTransfer(int userId, MachineTransfer machineTransfer)
    {
        //withdraw remove from bank account and banks own
        //deposit add to bank account and banks own
        Transaction machineTransaction = new Transaction(machineTransfer.getTransferType().toString(), LocalDateTime.now(), "NL13INHO1234567890", "NL13INHO1234567890", machineTransfer.getAmount(), userId);
        transactionRepository.save(machineTransaction);
        return machineTransaction;
    }
}
