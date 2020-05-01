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
        transactionRepository.save(new Transaction( Transaction.TransactionTypeEnum.TRANSACTION, "00:00", "NL12INHO1234567890", "NL13INHO1234567890", 44.44, 1 ));
        transactionRepository.save(new Transaction( Transaction.TransactionTypeEnum.TRANSACTION, "10:00", "NL12INHO1234567890", "NL13INHO1234567890", 88.44, 1 ));
        transactionRepository.findAll()
                .forEach(System.out::println);

        userRepository.save(new User("Pascalle", "Schipper", "pascalle.schipper@test.com", "password", User.UserTypeEnum.CUSTOMERANDEMPLOYEE));
        userRepository.findAll()
                .forEach(System.out::println);

        System.out.println("test");
    }
}

