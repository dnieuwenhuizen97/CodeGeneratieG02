package io.swagger.config;

import io.swagger.model.AuthToken;
import io.swagger.model.Transaction;

import io.swagger.repository.AuthTokenRepository;
import io.swagger.repository.TransactionRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class myApplicationRunner implements ApplicationRunner {

    private TransactionRepository repo;
    private AuthTokenRepository authTokenRepository;

    public myApplicationRunner(TransactionRepository repo, AuthTokenRepository authTokenRepository) {
        this.repo = repo;
        this.authTokenRepository = authTokenRepository;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        repo.save(new Transaction( Transaction.TransactionTypeEnum.TRANSACTION, "00:00", "NL12INHO1234567890", "NL13INHO1234567890", 44.44, 1 ));
        repo.save(new Transaction( Transaction.TransactionTypeEnum.TRANSACTION, "10:00", "NL12INHO1234567890", "NL13INHO1234567890", 88.44, 1 ));


        repo.findAll()
                .forEach(System.out::println);
        String token = "1234-abcd-5678-efgh";


        authTokenRepository.save(new AuthToken(token, 1000001, LocalDateTime.now()));

        authTokenRepository.findAll()
                .forEach(System.out::println);
    }
}

