package io.swagger.service;

import io.swagger.exceptions.CustomException;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public Page<Transaction> getAllTransactionsOfUser(Integer userId, Pageable pageable) {
        return transactionRepository.findByUserUserId(userId, pageable);
    }

    public Integer createMachineTransfer(int userId, double amount, String transfer_type)
    {
        //withdraw remove from bank account and banks own
        //deposit add to bank account and banks own
        transactionRepository.save(new Transaction(transfer_type, LocalDateTime.now(), "NL13INHO1234567890", "NL13INHO1234567890", amount, userId));
        return 201;
    }

    public Transaction createTransactionForUser(Transaction transaction, Integer userId) {
        // Validate if the user exists with the provided id. If not, then throw an exception.
        // Otherwise add the user reference in the transaction.
        transaction.setUser(userRepository.findById(userId).orElseThrow(() ->
                new CustomException(HttpStatus.NOT_FOUND, String.format("No user found for ID %s", userId))));
        return transactionRepository.save(transaction);
    }
}
