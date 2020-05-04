package io.swagger.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.api.TransactionsApi;
import io.swagger.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.service.AuthenticationService;
import io.swagger.service.TransactionService;
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

    public ResponseEntity<Void> createTransaction(@ApiParam(value = "Created transaction object" ,required=true )  @Valid @RequestBody Transaction body
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, 0))
            return new ResponseEntity(HttpStatus.FORBIDDEN);


        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
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

        return new ResponseEntity<List<Transaction>>(service.getAllTransactions(), HttpStatus.OK);

    }

    // Pageable added, that allows to have a paginated response for a user.
    // Instead of pulling all the records at the same time.
    @Override
    public ResponseEntity getAllTransactionsForUser(@Valid Integer userId,
                                                    Pageable pageable) {
        // Default message to if there are no transactions found.
        ResponseEntity responseEntity = ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body((JsonNode) objectMapper.createObjectNode()
                        .put("message", String.format("No transactions found for User ID %s", userId)));

        Page<Transaction> transactions = service.getAllTransactionsOfUser(userId, pageable);
        // If the transactions are available, then override the detault response.
        if (!transactions.isEmpty()) {
          //  responseEntity = ResponseEntity.ok().body(new ResponseWrapper(transactions));
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<Transaction> createTransactionForUser(@Valid Transaction transaction, @Valid Integer userId) {
        return ResponseEntity.ok().body(service.createTransactionForUser(transaction, userId));
    }

}
