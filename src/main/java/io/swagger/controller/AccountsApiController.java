package io.swagger.controller;

import io.swagger.api.AccountsApi;
import io.swagger.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-28T15:21:59.457Z[GMT]")
@Controller
public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private AuthenticationService authService;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request, AuthenticationService authService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.authService = authService;
    }

    public ResponseEntity<List<Account>> getAllAccounts(@ApiParam(value = "The number of items to skip before starting to collect the result set") @Valid @RequestParam(value = "offset", required = false) Integer offset
,@ApiParam(value = "The numbers of items to return") @Valid @RequestParam(value = "limit", required = false) Integer limit
,@ApiParam(value = "The user id of user that should own the account") @Valid @RequestParam(value = "userId", required = false) Integer userId
,@ApiParam(value = "The iban the account should have") @Valid @RequestParam(value = "iban", required = false) String iban
,@ApiParam(value = "The iban the account should have available accounts in the system", allowableValues = "current, savings") @Valid @RequestParam(value = "account_type", required = false) String accountType
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, 0))
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

}
