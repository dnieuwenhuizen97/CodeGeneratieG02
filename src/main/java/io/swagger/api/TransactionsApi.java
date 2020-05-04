/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.19).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.ResponseWrapper;
import io.swagger.model.Transaction;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-05-03T10:32:36.707Z[GMT]")
@Api(value = "transactions", description = "the transactions API")
public interface TransactionsApi {

    @ApiOperation(value = "Create new transaction", nickname = "createTransaction", notes = "Create a transaction to transfer money.", authorizations = {
        @Authorization(value = "ApiKeyAuth")    }, tags={ "Transactions","Customer operation", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Transaction has been created and money has been transferred."),
        @ApiResponse(code = 400, message = "Bad Request."),
        @ApiResponse(code = 401, message = "You are not authorized to create transaction."),
        @ApiResponse(code = 403, message = "You do not have the right function to transfer money, please contact your bank."),
        @ApiResponse(code = 404, message = "Something went wrong with your request."),
        @ApiResponse(code = 406, message = "Please check the Iban of the receiver and amount of money you want to transfer and try again."),
        @ApiResponse(code = 429, message = "You have tried too many times to create a new transaction, please wait a minute before you try again.") })
    @RequestMapping(value = "/transactions",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createTransaction(@ApiParam(value = "Created transaction object" ,required=true )  @Valid @RequestBody Transaction body
);


    @ApiOperation(value = "Gets all transactions", nickname = "getAllTransactions", notes = "", response = Transaction.class, responseContainer = "List", authorizations = {
        @Authorization(value = "ApiKeyAuth")    }, tags={ "Transactions","Employee operation", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "All transactions have been found.", response = Transaction.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request."),
        @ApiResponse(code = 401, message = "You are not authorized to search for all transactions."),
        @ApiResponse(code = 403, message = "You do not have the right function to search for transactions, please contact your employer."),
        @ApiResponse(code = 404, message = "No transaction has been found."),
        @ApiResponse(code = 406, message = "Invalid input, double check the values of the input fields and try again"),
        @ApiResponse(code = 429, message = "You have tried too many times to search for all transactions, please wait a minute before you try again.") })
    @RequestMapping(value = "/transactions",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Transaction>> getAllTransactions(@ApiParam(value = "The number of items to skip before starting to collect the result set") @Valid @RequestParam(value = "offset", required = false) Integer offset
,@ApiParam(value = "The numbers of items to return") @Valid @RequestParam(value = "limit", required = false) Integer limit
,@ApiParam(value = "The id of the user thats should ne involved within the transaction") @Valid @RequestParam(value = "userId", required = false) Integer userId
,@ApiParam(value = "The iban that should be involved within the transactions") @Valid @RequestParam(value = "iban", required = false) String iban
);



    @ApiOperation(value = "Gets all transactions for user", nickname = "getUserTransactions", notes = "", response = ResponseWrapper.class, responseContainer = "List", authorizations = {
            @Authorization(value = "ApiKeyAuth")}, tags = {"Transactions", "Employee operation",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All transactions have been found.", response = ResponseWrapper.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request."),
            @ApiResponse(code = 401, message = "You are not authorized to search for all transactions."),
            @ApiResponse(code = 403, message = "You do not have the right function to search for transactions, please contact your employer.")})
    @RequestMapping(value = "/transactions/user/{userId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Page<Transaction>> getAllTransactionsForUser(@ApiParam(value = "The id of the user for with the transactions are being fetched") @Valid @PathVariable(value = "userId") Integer userId,
                                                                Pageable pageable);


    @ApiOperation(value = "Create new transaction for user", nickname = "createTransactionForUser", notes = "Create a transaction to transfer money.", authorizations = {
            @Authorization(value = "ApiKeyAuth")}, tags = {"Transactions", "Customer operation",})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Transaction has been created and money has been transferred."),
            @ApiResponse(code = 400, message = "Bad Request."),
            @ApiResponse(code = 401, message = "You are not authorized to create transaction."),
            @ApiResponse(code = 403, message = "You do not have the right function to transfer money, please contact your bank."),
            @ApiResponse(code = 404, message = "Something went wrong with your request."),
            @ApiResponse(code = 406, message = "Please check the Iban of the receiver and amount of money you want to transfer and try again."),
            @ApiResponse(code = 429, message = "You have tried too many times to create a new transaction, please wait a minute before you try again.")})
    @RequestMapping(value = "/transactions/user/{userId}",
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Transaction> createTransactionForUser(@ApiParam(value = "Created transaction object", required = true) @Valid @RequestBody Transaction transaction,
                                                         @ApiParam(value = "The id of the user for which the transactions is being created") @Valid @PathVariable(value = "userId") Integer userId);

}
