package io.swagger.config;

import io.swagger.model.*;

import io.swagger.repository.*;

import io.swagger.service.GeneralMethodsService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

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
    private RegisterRequestRepository registerRequestRepository;
    private GeneralMethodsService generalMethodsService;

    public myApplicationRunner(TransactionRepository transactionRepository, AuthTokenRepository authTokenRepository, UserRepository userRepository, AccountRepository accountRepository, RegisterRequestRepository registerRequestRepository, GeneralMethodsService generalMethodsService) {
        this.transactionRepository = transactionRepository;
        this.authTokenRepository = authTokenRepository;
        this.userRepository = userRepository;
        this.expiredTokenDeleteTimer = new Timer();
        this.accountRepository = accountRepository;
        this.registerRequestRepository = registerRequestRepository;
        this.generalMethodsService = generalMethodsService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        /*       Files.lines(Paths.get("address.csv"))
                .forEach(
                        line -> addressRepository.save(
                                new Address(line.split(",")[0],
                                        line.split(",")[1],
                                        line.split(",")[2],
                                        line.split(",")[3])
                                        //check how many lines.
                        )
                );
*/



       registerRequestRepository.save(new RegisterRequest("Pascalle", "Schipper", generalMethodsService.cryptWithMD5("password"), "pa@test.com"));

        transactionRepository.save(new Transaction( "transaction", LocalDateTime.now(), "NL12INHO1234567890", "NL13INHO1234567890", 44.44, 100053 ));
        transactionRepository.save(new Transaction( "transaction", LocalDateTime.now(), "NL13INHO1234567890", "NL13INHO1234567890", 44.44, 100053 ));
        transactionRepository.save(new Transaction( "transaction", LocalDateTime.now(), "NL14INHO1234567890", "NL13INHO1234567890", 44.44, 100052));
        transactionRepository.findAll()
                .forEach(System.out::println);

        userRepository.save(new User("Bank", "CodeGeneratie", generalMethodsService.cryptWithMD5("password"),"BankCodeGeneratie",  "employee"));
        userRepository.save(new User("employee", "employee", generalMethodsService.cryptWithMD5("password"),"employee",  "employee"));
        userRepository.save(new User("customer", "customer", generalMethodsService.cryptWithMD5("password"),"customer",  "customer"));
        userRepository.save(new User("savings", "customer", generalMethodsService.cryptWithMD5("password"),"customerWithOnlySavings",  "customer"));
        userRepository.findAll()
                .forEach(System.out::println);

        authTokenRepository.save(new AuthToken("1234-abcd-5678-efgh", 100001, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30)));
        authTokenRepository.save(new AuthToken("1111-abcd-5678-efgh", 100003, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30)));
        authTokenRepository.findAll()
                .forEach(System.out::println);


        accountRepository.save(new Account("NL01INHO0000000001","current", 1, new BigDecimal(3500), new BigDecimal(35000000), 100001, 100.00));

        accountRepository.save(new Account("NL05INHO0993873040","savings", 200, new BigDecimal(3500), new BigDecimal(35000000), 100002, 25.00));
        accountRepository.save(new Account("NL88INHO0993873040","current", 200, new BigDecimal(3500), new BigDecimal(35000000), 100002, 25.00));

        accountRepository.save(new Account("NL04INHO0463973767", "current", 200, new BigDecimal(3500), new BigDecimal(35000000), 100003, 25.00));
        accountRepository.save(new Account("NL67INHO0463973767", "savings", 200, new BigDecimal(3500), new BigDecimal(35000000), 100003, 25.00));

        accountRepository.save(new Account("NL67INHO0463973768", "savings", 200, new BigDecimal(3500), new BigDecimal(35000000), 100004, 25.00));

        accountRepository.findAll()
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

