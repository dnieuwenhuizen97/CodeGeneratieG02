package io.swagger.config;

import io.swagger.model.Account;
import io.swagger.model.AuthToken;
import io.swagger.model.Transaction;

import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.AuthTokenRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import io.swagger.service.AuthenticationService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class myApplicationRunner implements ApplicationRunner {

    private TransactionRepository transactionRepository;
    private AuthTokenRepository authTokenRepository;
    private UserRepository userRepository;
    private Timer expiredTokenDeleteTimer;
    private AccountRepository accountRepository;

    public myApplicationRunner(TransactionRepository transactionRepository, AuthTokenRepository authTokenRepository, UserRepository userRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.authTokenRepository = authTokenRepository;
        this.userRepository = userRepository;
        this.expiredTokenDeleteTimer = new Timer();
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {


        authTokenRepository.save(new AuthToken("1234-abcd-5678-efgh", 100052, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30)));
        authTokenRepository.findAll()
                .forEach(System.out::println);

        accountRepository.save(new Account("NL05INHO0993873040","savings", 200, new BigDecimal(3500), new BigDecimal(35000000), 100053, 25.00)); // customer 100053 current account
        accountRepository.save(new Account("NL88INHO0993873040","current", 200, new BigDecimal(3500), new BigDecimal(35000000), 100053, 25.00)); // customer 100053 saving account
        accountRepository.save(new Account("NL04INHO0463973767", "current", 200, new BigDecimal(3500), new BigDecimal(35000000), 100052, 25.00));// employee 100052 current account
        accountRepository.save(new Account("NL67INHO0463973767", "savings", 200, new BigDecimal(3500), new BigDecimal(35000000), 100052, 25.00));// employee 100052 saving account
        accountRepository.save(new Account("NL15INHO0463973767", "savings", 200, new BigDecimal(3500), new BigDecimal(35000000), 100052, 25.00));// employee 100052 saving account
        accountRepository.findAll()
                .forEach(System.out::println);

        transactionRepository.save(new Transaction( "transaction", LocalDateTime.now(), "NL88INHO0993873040", "NL05INHO0993873040", 30.44, 100052 )); // employee doing transaction for customer
        transactionRepository.save(new Transaction( "transaction", LocalDateTime.now(), "NL04INHO0463973767", "NL67INHO0463973767", 40.44, 100052 )); // employee doing transaction for himself
        transactionRepository.save(new Transaction( "transaction", LocalDateTime.now(), "NL88INHO0993873040", "NL05INHO0993873040", 50.44, 100053 )); // customer doing transaction for himself
        transactionRepository.findAll()
                .forEach(System.out::println);

        userRepository.save(new User("employee", "employee", "password","employee",  "employee")); // 100052
        userRepository.save(new User("customer", "customer", "password","customer",  "customer")); // 100053
        userRepository.findAll()
                .forEach(System.out::println);


        //delete tokens after they are expired

        int delay = 5000;   // delay for 5 sec.
        int period = 3000;  // repeat every 3 sec.

        expiredTokenDeleteTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                authTokenRepository.DeleteAuthTokenByDate(LocalDateTime.now());
            }
        }, delay, period);

    }
}

