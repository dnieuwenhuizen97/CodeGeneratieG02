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

    public void saveAccount(Account account) { accountRepository.save(account); }

    public Account getSpecificAccount(String id) { return accountRepository.findAccountByIban(id); }

    public List<Account> getAllAccounts() {
        return (List<Account>)accountRepository.findAll();
    }

    public void createAccount(Account a) { accountRepository.save(a); }

    public void deleteAccount(Account a) { accountRepository.delete(a); }

    public String CreateIban()
    {
        String iban = "";
        Random r = new Random();

        List<String> ibanParts = new ArrayList<>();
        ibanParts.add("NL");
        ibanParts.add("");
        ibanParts.add("INHO");
        ibanParts.add("0");

        int partCounter = 1;
        for(String part : ibanParts) {
            for (int i = 1; i <= 2; i++) {
                if(partCounter / 2 == 1)
                    part += Integer.toString(r.nextInt(10));
            }
            for (int i = 1; i <= 9; i++)
                if(partCounter / 2 == 2)
                    part += Integer.toString(r.nextInt(10));
        }
        return iban;
    }
}
