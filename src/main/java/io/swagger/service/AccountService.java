
package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository repository) {
        this.accountRepository = repository;
    }

    public Account getSpecificAccount(String iban) {
        return accountRepository.findById(iban).get();
    }

    public List<Account> getAllAccounts() {

        return (List<Account>)accountRepository.findAll();
    }

    public List<Account> getAllAccountsFromUser(Integer userId) {

        return (List<Account>)accountRepository.findAccountByOwner(userId);
    }

    public Account createAccount(Account newAccount, Integer userId){
            newAccount.setIban(CreateIban());
            newAccount.setOwner(userId);
            accountRepository.save(newAccount);
            return newAccount;
    }

    public Integer deleteAccount(String iban)
    {
        Account aboutToBeDeletedAccount = accountRepository.findById(iban).get();
        if(!accountRepository.existsById(iban))
            return 406;
        if(aboutToBeDeletedAccount.getBalance() < 0.00)
            return 403;
        accountRepository.deleteById(iban);
        return 204;
    }

    public Account updateAccount(Account account, String iban)
    {
        Account oldAccount = accountRepository.findById(iban).get();

        oldAccount.setBalanceLimit(account.getBalanceLimit());
        oldAccount.setTransactionAmountLimit(account.getTransactionAmountLimit());
        oldAccount.setTransactionDayLimit(account.getTransactionDayLimit());

        accountRepository.save(oldAccount);
        return oldAccount;
    }

    public String CreateIban()
    {
        String iban = "NL54INHO0123456789";
//        Random r = new Random();
//
//        iban += "NL";
//        for(int i = 1; i <=2; i++)
//        {
//            iban += Integer.toString(r.nextInt(10));
//        }
//
//        iban += "INHO0";
//        for(int i = 1; i <=9; i++)
//        {
//            iban += Integer.toString(r.nextInt(10));
//        }
        return iban;
    }
}




