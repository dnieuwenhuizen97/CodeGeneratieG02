package io.swagger.service;

import io.swagger.model.Transaction;
import org.springframework.stereotype.Service;
import io.swagger.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionService {
    TransactionRepository repo;

    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
    }
    public List<Transaction> getAllTransactions()
    {
        return (List<Transaction>)repo.findAll();
    }
}
