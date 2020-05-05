package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository repository) { this.accountRepository = repository; }

    public void saveAccount(Account account) { accountRepository.save(account); }

    public Account getSpecificAccount(String id)
    {
        return accountRepository.findAccountById(id);
    }

    public List<Account> getAllAccounts() {
        return (List<Account>)accountRepository.findAll();
    }

    public void createAccount(Account a) { accountRepository.save(a); }

    public void deleteAccount(Account a) { accountRepository.delete(a); }
}
