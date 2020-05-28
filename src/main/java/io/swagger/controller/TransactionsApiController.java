package io.swagger.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.api.TransactionsApi;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.model.User;
import io.swagger.service.AuthenticationService;
import io.swagger.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-28T15:21:59.457Z[GMT]")
@Controller
public class TransactionsApiController implements TransactionsApi {
    private TransactionService service;
    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private AuthenticationService authService;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request, TransactionService service, AuthenticationService authService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.service = service;
        this.authService = authService;
    }

    public ResponseEntity<Transaction> createTransaction(@ApiParam(value = "Created transaction object" ,required=true)  @Valid @RequestBody Transaction transaction    ) {

        Integer userId = 100053; // Static user, get the logged in user here
        String apiKeyAuth = request.getHeader("ApiKeyAuth");

        if(!authService.IsUserAuthenticated(apiKeyAuth, 0, false))
            return new ResponseEntity(HttpStatus.FORBIDDEN);


        Account account = findAccountByUserId(userId);
        User user;

        String message = null;

        //Still static
        //First two checks: user is only able to transfer if it's the same userId or type is employee
        //AND the sender is sending money with his own IBAN
        //Check if amount is higher than 0
        if(transaction.getAmount() < 0){
            message = "You cannot transfer a negative number.";
        }
        //Customer cannot transfer 0 (nothing)
        else if(transaction.getAmount() == 0){
            message = "You cannot transfer nothing.";
        }
        //Check is transfer is higher than balance
        else if(account.getBalance() < transaction.getAmount()){
            message = "You do not have enough balance to transfer this amount!";
        }
        //Unable to transfer funds to another savings (besides your own)
        else if (account.getIban().equals(transaction.getAccountFrom()) && account.getAccountType() == Account.AccountTypeEnum.SAVINGS){
            message = "You cannot transfer the funds to a savings account.";
        }
        //Needs to be changed to the absolute limit
        else if(account.getBalance() < -1200){
            message = "Your have extended your absolute limit, please deposit money first.";
        }
        //A user has a maximum of transactions per day
        else if(account.getTransactionDayLimit() >= 100){
            message = "You have reached your day limit of 100 transactions.";
        }
        //Amount of transactions per day
        else if(account.getTransactionAmountLimit() >= 200){
            message = "You have reached your transaction limit, please wait until tomorrow.";
        }
        //Unable to transfer to own account (Account to is the same as account from)
        else if(transaction.getAccountFrom().equals(transaction.getAccountTo())){
            message = "You cannot transfer to your own account!";
        }

        else if(transaction.getUserPerforming() == userId) { //Need to add employee type too
            message = "You are trying to perform a transaction on someone else!";
        }
        /*
           else if(verifyAccountTo(transaction.getAccountTo())){
             message = "You cannot transfer nothing";
        }*/


        ResponseEntity responseEntity = null;
        if(message != null){
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                    (JsonNode) objectMapper.createObjectNode().put("message",
                            message));
            return responseEntity;
        }

        return new ResponseEntity<Transaction>(service.createTransactionForUser(transaction), HttpStatus.CREATED);
    }

    public ResponseEntity<List<Transaction>> getAllTransactions(@ApiParam(value = "The number of items to skip before starting to collect the result set") @Valid @RequestParam(value = "offset", required = false) Integer offset
            ,@ApiParam(value = "The numbers of items to return") @Valid @RequestParam(value = "limit", required = false) Integer limit
            ,@ApiParam(value = "The id of the user thats should ne involved within the transaction") @Valid @RequestParam(value = "userId", required = false) Integer userId
            ,@ApiParam(value = "The iban that should be involved within the transactions") @Valid @RequestParam(value = "iban", required = false) String iban
    ) {
        //get auth token
        String accept = request.getHeader("Accept");

        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Transaction>>(objectMapper.readValue("[ {\n  \"transaction_id\" : 10034,\n  \"amount\" : 22.30,\n  \"account_to\" : \"NL39ING008451843\",\n  \"account_from\" : \"NL39INGB007801007\",\n  \"transaction_type\" : [ \"withdraw\", \"withdraw\" ],\n  \"user_performing\" : 1,\n  \"timestamp\" : \"995-09-07T10:40:52Z\"\n}, {\n  \"transaction_id\" : 10034,\n  \"amount\" : 22.30,\n  \"account_to\" : \"NL39ING008451843\",\n  \"account_from\" : \"NL39INGB007801007\",\n  \"transaction_type\" : [ \"withdraw\", \"withdraw\" ],\n  \"user_performing\" : 1,\n  \"timestamp\" : \"995-09-07T10:40:52Z\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        //if not authenticated
        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, 0, true))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        //if there are 0 transactions, show a message instead of nothing
        if(service.getAllTransactions().size() == 0){
            try {
                return new ResponseEntity<List<Transaction>>(objectMapper.readValue("[ \"No transactions found\"  ]", List.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<List<Transaction>>(service.getAllTransactions(), HttpStatus.OK);
        }

    }

    //Pagable is used in order to allow a paginated response for a user instead of pulling all the records at the time.
    /*@Override
    public ResponseEntity getAllTransactionsForUser(@Valid Integer userId,
                                                    Pageable pageable) {
        ResponseEntity responseEntity = null;
        String responseMessage = null;

        User user  = userService.FindUserById(userId);
        if (user == null) {
            // no user found for give userId
            responseMessage = String.format("The user id you entered is wrong %s",
                    userId);
        } else if (user.getUserType() != User.UserTypeEnum.EMPLOYEE) {
            //user is not an employee
            responseMessage = String.format("You cannot search for all transactions.");
        }else {
            responseMessage = String.format("failed.");

        }

        Page<Transaction> transactions = service.getAllTransactionsOfUser(userId, pageable);

        if(transactions.isEmpty()){
            // if there are no transactions found.
            responseMessage = String.format("No transactions found for User ID %s", userId);
        }else {
            responseEntity = ResponseEntity.ok().body(new ResponseWrapper(transactions));
        }

        //If responseMessage is set create a response entity with responseMessage
        if (responseMessage != null) {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    (JsonNode) objectMapper.createObjectNode().put("message",
                            responseMessage));
        }

        return responseEntity;
    }*/



    //Static account (needs to be changed)
    private Account findAccountByUserId(Integer userId) {

        // TODO Find account by calling AccountService

        Account account =  new Account();
        account.setIban("NL12INHO1234567890");
        account.setBalance(1000.00f);
        account.setOwner(userId);
        account.setTransactionAmountLimit(10);
        account.setTransactionDayLimit(10);

        account.setAccountType(Account.AccountTypeEnum.CURRENT);
        return account;
    }


    /**
     * validate a account by accountId
     * @param account
     * @return true if account exist
     */
    private boolean verifyAccountTo( String accountID) {

        // TODO get account by accountID from account service

        return true;
    }
}
