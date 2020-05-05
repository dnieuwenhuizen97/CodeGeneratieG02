package io.swagger.controller;

import io.swagger.api.UserApi;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.service.AuthenticationService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-28T15:21:59.457Z[GMT]")
@Controller
public class UserApiController implements UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private AuthenticationService authService;

    private UserService userService;

    private TransactionService transactionService;

    @org.springframework.beans.factory.annotation.Autowired
    public UserApiController(ObjectMapper objectMapper, HttpServletRequest request, AuthenticationService authService, UserService userService, TransactionService transactionService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.authService = authService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    public ResponseEntity<Void> createAccountByUser(@ApiParam(value = "user of a specific account",required=true) @PathVariable("userId") Integer userId
,@ApiParam(value = "Created account object"  )  @Valid @RequestBody Account body
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, userId))
            return new ResponseEntity(HttpStatus.FORBIDDEN);


        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteUserById(@ApiParam(value = "The id from the user",required=true) @PathVariable("userId") Integer userId
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, userId))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        return new ResponseEntity<Void>(HttpStatus.valueOf(userService.DeleteUserById(userId)));
    }

    public ResponseEntity<List<Account>> getAccountsByUser(@ApiParam(value = "user of a specific account",required=true) @PathVariable("userId") Integer userId
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, userId))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Account>>(objectMapper.readValue("[ {\n  \"owner\" : 1,\n  \"account_type\" : [ \"current\", \"current\" ],\n  \"transactionDayLimit\" : 100,\n  \"balance\" : 200,\n  \"transactionAmountLimit\" : 200,\n  \"iban\" : \"NL11INHO0123456789\",\n  \"balanceLimit\" : -1200\n}, {\n  \"owner\" : 1,\n  \"account_type\" : [ \"current\", \"current\" ],\n  \"transactionDayLimit\" : 100,\n  \"balance\" : 200,\n  \"transactionAmountLimit\" : 200,\n  \"iban\" : \"NL11INHO0123456789\",\n  \"balanceLimit\" : -1200\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Account>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Account>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Transaction>> getAllTransactionsFromUser(@ApiParam(value = "Get details of transaction based on iban",required=true) @PathVariable("userId") Integer userId
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, userId))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Transaction>>(objectMapper.readValue("[ {\n  \"transaction_id\" : 10034,\n  \"amount\" : 22.30,\n  \"account_to\" : \"NL39ING008451843\",\n  \"account_from\" : \"NL39INGB007801007\",\n  \"transaction_type\" : [ \"withdraw\", \"withdraw\" ],\n  \"user_performing\" : 1,\n  \"timestamp\" : \"995-09-07T10:40:52Z\"\n}, {\n  \"transaction_id\" : 10034,\n  \"amount\" : 22.30,\n  \"account_to\" : \"NL39ING008451843\",\n  \"account_from\" : \"NL39INGB007801007\",\n  \"transaction_type\" : [ \"withdraw\", \"withdraw\" ],\n  \"user_performing\" : 1,\n  \"timestamp\" : \"995-09-07T10:40:52Z\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Transaction>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<User> getUserById(@ApiParam(value = "The id from the user",required=true) @PathVariable("userId") Integer userId
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, userId))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        return new ResponseEntity<User>(userService.FindUserById(userId),HttpStatus.OK);
    }

    public ResponseEntity<Void> machineTransfer(@ApiParam(value = "",required=true) @PathVariable("userId") Integer userId
,@NotNull @ApiParam(value = "Amount that has to be transfered", required = true) @Valid @RequestParam(value = "amount", required = true) double amount
,@NotNull @ApiParam(value = "Tranfer type withdraw or deposit", required = true, allowableValues = "deposit, withdraw") @Valid @RequestParam(value = "transfer_type", required = true) String transferType
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, userId))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        return new ResponseEntity<Void>(HttpStatus.valueOf(transactionService.createMachineTransfer(userId ,amount, transferType)));
    }

    public ResponseEntity<Void> updateUserById(@ApiParam(value = "" ,required=true )  @Valid @RequestBody User body
,@ApiParam(value = "The id from the user",required=true) @PathVariable("userId") Integer userId
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, userId))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        return new ResponseEntity<Void>(HttpStatus.valueOf(userService.UpdateUserById(body)));
    }

}
