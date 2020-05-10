package io.swagger.config;

import io.swagger.model.AuthToken;
import io.swagger.model.Transaction;

import io.swagger.model.User;
import io.swagger.repository.AuthTokenRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class myApplicationRunner implements ApplicationRunner {

    private TransactionRepository transactionRepository;
    private AuthTokenRepository authTokenRepository;
    private UserRepository userRepository;

    public myApplicationRunner(TransactionRepository transactionRepository, AuthTokenRepository authTokenRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.authTokenRepository = authTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        transactionRepository.save(new Transaction( "transaction", LocalDateTime.now(), "NL12INHO1234567890", "NL13INHO1234567890", 44.44, 10001 ));
        transactionRepository.findAll()
                .forEach(System.out::println);

        userRepository.save(new User("employee", "employee", "password","employee",  "employee"));
        userRepository.save(new User("customer", "customer", "password","customer",  "customer"));
        userRepository.findAll()
                .forEach(System.out::println);

        authTokenRepository.save(new AuthToken("1234-abcd-5678-efgh", 100052, LocalDateTime.now()));
        authTokenRepository.findAll()
                .forEach(System.out::println);
    }
}

