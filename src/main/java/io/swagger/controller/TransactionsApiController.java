package io.swagger.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.api.TransactionsApi;
import io.swagger.model.ResponseWrapper;
import io.swagger.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.model.User;
import io.swagger.service.AuthenticationService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-28T15:21:59.457Z[GMT]")
@Controller
public class TransactionsApiController implements TransactionsApi {
    private TransactionService service;
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private AuthenticationService authService;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request, TransactionService service, AuthenticationService authService , UserService userService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.service = service;
        this.authService = authService;
        this.userService = userService;
    }

    public ResponseEntity<Transaction> createTransaction(@ApiParam(value = "Created transaction object" ,required=true )  @Valid @RequestBody Transaction transaction    ) {

        Integer userId = 100053; // default user  TODO  get the logged_in user here
        String apiKeyAuth = request.getHeader("ApiKeyAuth");

        if(!authService.IsUserAuthenticated(apiKeyAuth, 0))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

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
        if(!authService.IsUserAuthenticated(apiKeyAuth, 0))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        //if there are 0 transactions
        if(service.getAllTransactions().size() == 0 ){
            try {
                return new ResponseEntity<List<Transaction>>(objectMapper.readValue("[ \"No transactions found\"  ]", List.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{

            return new ResponseEntity<List<Transaction>>(service.getAllTransactions(), HttpStatus.OK);
        }

    }

    // Pageable added, that allows to have a paginated response for a user.
    // Instead of pulling all the records at the same time.
    @Override
    public ResponseEntity<Page<Transaction>> getAllTransactionsForUser(@Valid Integer userId, Pageable pageable) {
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
    }

    /*
    @Override
    public ResponseEntity<Transaction> createTransactionForUser(@Valid Transaction transaction) {
        return ResponseEntity.ok().body(service.createTransactionForUser(transaction));
    }*/

}