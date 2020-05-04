package io.swagger.service;

import com.sun.org.apache.xpath.internal.objects.XString;
import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private AccountRepository repository;

    public AccountService(AccountRepository repository) { this.repository = repository; }

    public List<Account> getAllAccounts() { return (List<Account>)repository.findAll(); }

    public void saveAccount(Account account) { repository.save(account); }

    public Account getAccountByIban (long iban) {
        Account a = repository.findById(iban);
        return a;
    }

}
