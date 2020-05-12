package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository repository) { this.accountRepository = repository; }

    public Account getSpecificAccount(String iban) { return accountRepository.findById(iban).get(); }

    public List<Account> getAllAccounts() {
        return (List<Account>)accountRepository.findAll();
    }

    public void createAccount(Account a) { accountRepository.save(a); }

    public Integer deleteAccount(String iban)
    {
        if(!accountRepository.existsById(iban))
            return 406;
        accountRepository.deleteById(iban);
        return 201;
    }

    public Integer updateAccount(Account a)
    {
        //if(!accountRepository.existsById(a.getIban()))
            return 406;
    }
}
