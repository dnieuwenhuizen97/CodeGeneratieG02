package io.swagger.config;

import io.swagger.controller.UserApiController;
import io.swagger.model.AuthToken;
import io.swagger.model.Transaction;

import io.swagger.model.User;
import io.swagger.repository.AuthTokenRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import io.swagger.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.ls.LSOutput;

import java.time.LocalDateTime;

@Component
public class myApplicationRunner implements ApplicationRunner {

    private TransactionRepository transactionRepository;
    private AuthTokenRepository authTokenRepository;
    private UserRepository userRepository;
    private UserService userService;

    public myApplicationRunner(TransactionRepository transactionRepository, AuthTokenRepository authTokenRepository, UserRepository userRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.authTokenRepository = authTokenRepository;
        this.userRepository = userRepository;
        this.userService = userService;
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

        userService.UpdateUserById("Dylan", "Nieuwenhuizen", "newPassword", "newEmployeeEmail", userRepository.findUserById(100052));
        userService.UpdateUserById("test", "testt", "newPassword", "newCustomerEmail", userRepository.findUserById(100053));

        System.out.println("\n-----> HERE'S THE UPDATED VERSION <-----\n");
        userRepository.findAll()
                .forEach(System.out::println);

        authTokenRepository.save(new AuthToken("1234-abcd-5678-efgh", 100001, LocalDateTime.now()));
        authTokenRepository.findAll()
                .forEach(System.out::println);
    }
}

