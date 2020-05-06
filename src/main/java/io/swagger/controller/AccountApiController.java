package io.swagger.controller;

import io.swagger.api.AccountApi;
import io.swagger.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.service.AccountService;
import io.swagger.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-28T15:21:59.457Z[GMT]")
@Controller
public class AccountApiController implements AccountApi {

    private AccountService service;

    private static final Logger log = LoggerFactory.getLogger(AccountApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private AuthenticationService authService;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountApiController(ObjectMapper objectMapper, HttpServletRequest request, AuthenticationService authService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.authService = authService;

        service.createAccount(new Account("NL84INHO0753754372", "savings", 3000f, 200, new BigDecimal(3500), new BigDecimal(35000000), 10002));
        service.createAccount(new Account("NL84INHO0753764892", "current", 6000f, 200, new BigDecimal(3500), new BigDecimal(35000000), 10003));
    }

    public ResponseEntity<Void> deleteAccountByIban(@ApiParam(value = "iban of a specific account",required=true) @PathVariable("iban") String iban
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, 0))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Account> getSpecificAccount(@ApiParam(value = "user of a specific account",required=true) @PathVariable("iban") String iban
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, 0))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Account>(objectMapper.readValue("{\n  \"owner\" : 1,\n  \"account_type\" : [ \"current\", \"current\" ],\n  \"transactionDayLimit\" : 100,\n  \"balance\" : 200,\n  \"transactionAmountLimit\" : 200,\n  \"iban\" : \"NL11INHO0123456789\",\n  \"balanceLimit\" : -1200\n}", Account.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Account> updateSpecificAccount(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Account body
,@ApiParam(value = "user of a specific account",required=true) @PathVariable("iban") String iban
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, 0))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Account>(objectMapper.readValue("{\n  \"owner\" : 1,\n  \"account_type\" : [ \"current\", \"current\" ],\n  \"transactionDayLimit\" : 100,\n  \"balance\" : 200,\n  \"transactionAmountLimit\" : 200,\n  \"iban\" : \"NL11INHO0123456789\",\n  \"balanceLimit\" : -1200\n}", Account.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Account>(HttpStatus.NOT_IMPLEMENTED);
    }

}
