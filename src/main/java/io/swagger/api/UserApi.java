/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.19).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.annotations.*;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-28T15:21:59.457Z[GMT]")
@Api(value = "user", description = "the user API")
public interface UserApi {

    @ApiOperation(value = "Create account for user", nickname = "createAccountByUser", notes = "Creates account for user", authorizations = {
        @Authorization(value = "ApiKeyAuth")    }, tags={ "Accounts","Customer operation", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Account has been created!"),
        @ApiResponse(code = 400, message = "Bad Request."),
        @ApiResponse(code = 401, message = "You are not authorized to create an account."),
        @ApiResponse(code = 403, message = "You do not have the right function to create accounts, please contact your employer."),
        @ApiResponse(code = 404, message = "Something went wrong with your request."),
        @ApiResponse(code = 406, message = "Invalid input, double check the values of the fields and try again."),
        @ApiResponse(code = 429, message = "You have tried too many times to create an account, please wait a minute before you try again.") })
    @RequestMapping(value = "/user/{userId}/accounts",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createAccountByUser(@ApiParam(value = "user of a specific account",required=true) @PathVariable("userId") Integer userId
,@ApiParam(value = "Created account object"  )  @Valid @RequestBody Account body
);


    @ApiOperation(value = "Delete a single user by id", nickname = "deleteUserById", notes = "Deletes a single user from the database by the user id that has been given.", authorizations = {
        @Authorization(value = "ApiKeyAuth")    }, tags={ "Users","Employee operation", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User has successfully been deleted."),
        @ApiResponse(code = 400, message = "Bad Request."),
        @ApiResponse(code = 401, message = "You are not authorized to delete a user."),
        @ApiResponse(code = 403, message = "You do not have the right function to delete users, please contact your employer."),
        @ApiResponse(code = 404, message = "Something went wrong with your request."),
        @ApiResponse(code = 406, message = "Invalid input, double check the values of the input fields and try again."),
        @ApiResponse(code = 429, message = "You have tried too many times to create a user, please wait a minute before you try again.") })
    @RequestMapping(value = "/user/{userId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteUserById(@ApiParam(value = "The id from the user",required=true) @PathVariable("userId") Integer userId
);


    @ApiOperation(value = "Get all account from user", nickname = "getAccountsByUser", notes = "By passing in the appropriate options, you can search for one perticular account in the system by user ", response = Account.class, responseContainer = "List", authorizations = {
        @Authorization(value = "ApiKeyAuth")    }, tags={ "Accounts","Customer operation", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "All accounts of one specific user has been found.", response = Account.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request."),
        @ApiResponse(code = 401, message = "You are not authorized to get all accounts of one specific user."),
        @ApiResponse(code = 403, message = "Forbidden to search for accounts."),
        @ApiResponse(code = 404, message = "No match has been found, please try again."),
        @ApiResponse(code = 406, message = "Invalid input, double check the values of the input fields, please try again."),
        @ApiResponse(code = 429, message = "You are tried too many times to search all accounts of one specific user, please wait a minute before you try again.") })
    @RequestMapping(value = "/user/{userId}/accounts",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Account>> getAccountsByUser(@ApiParam(value = "user of a specific account",required=true) @PathVariable("userId") Integer userId
);


    @ApiOperation(value = "Retrieve all transactions from user", nickname = "getAllTransactionsFromUser", notes = "Get all transactions within the user is involved", response = Transaction.class, responseContainer = "List", authorizations = {
        @Authorization(value = "ApiKeyAuth")    }, tags={ "Transactions","Customer operation", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "All transactions from one specific user has been found.", response = Transaction.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request."),
        @ApiResponse(code = 401, message = "You are not authorized to retrieve all transactions of a specific user."),
        @ApiResponse(code = 403, message = "You do not have the right function to retrieve all transactions from one specific user."),
        @ApiResponse(code = 404, message = "Something went wrong with your request."),
        @ApiResponse(code = 406, message = "Double check the values of the fields and try again"),
        @ApiResponse(code = 429, message = "You have tried too many times to retrieve all transactions from user, please wait a minute before you try again.") })
    @RequestMapping(value = "/user/{userId}/transaction",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Transaction>> getAllTransactionsFromUser(@ApiParam(value = "Get details of transaction based on iban",required=true) @PathVariable("userId") Integer userId
);


    @ApiOperation(value = "Get user by id", nickname = "getUserById", notes = "Get user by the given id from the database", authorizations = {
        @Authorization(value = "ApiKeyAuth")    }, tags={ "Users","Customer operation", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User have been found."),
        @ApiResponse(code = 400, message = "Bad Request."),
        @ApiResponse(code = 401, message = "You are not authorized to search for a specific user."),
        @ApiResponse(code = 403, message = "You do not have the right function to search for a user, please contact your employer."),
        @ApiResponse(code = 404, message = "Something went wrong with your request."),
        @ApiResponse(code = 406, message = "Invalid input, double check the values of the fields and try again."),
        @ApiResponse(code = 429, message = "You have tried too many times to search a user, please wait a minute before you try again.") })
    @RequestMapping(value = "/user/{userId}",
        method = RequestMethod.GET)
    ResponseEntity<User> getUserById(@ApiParam(value = "The id from the user",required=true) @PathVariable("userId") Integer userId
);


    @ApiOperation(value = "withdraw/deposit money by user.", nickname = "machineTransfer", notes = "Withdraw or deposit money, depends on the type if the money will be added or removed from the account.", authorizations = {
        @Authorization(value = "ApiKeyAuth")    }, tags={ "Machine","Customer operation", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Withdraw/desposit succesfully processed."),
        @ApiResponse(code = 400, message = "Bad Request."),
        @ApiResponse(code = 401, message = "You are not authorized to either withdraw or deposit money."),
        @ApiResponse(code = 403, message = "You do not have the right function to either withdraw or deposit money ."),
        @ApiResponse(code = 404, message = "Something went wrong with your request."),
        @ApiResponse(code = 406, message = "Invalid input, double check the values of the fields and try again."),
        @ApiResponse(code = 429, message = "You have tried too many times to withdraw or deposit money, please wait a minute before you try again.") })
    @RequestMapping(value = "/user/{userId}/machine",
        method = RequestMethod.POST)
    ResponseEntity<Void> machineTransfer(@ApiParam(value = "",required=true) @PathVariable("userId") Integer userId
,@NotNull @ApiParam(value = "Amount that has to be transfered", required = true) @Valid @RequestParam(value = "amount", required = true) Float amount
,@NotNull @ApiParam(value = "Tranfer type withdraw or deposit", required = true, allowableValues = "deposit, withdraw") @Valid @RequestParam(value = "transfer_type", required = true) String transferType
);


    @ApiOperation(value = "Updates user", nickname = "updateUserById", notes = "Update user with the given information", authorizations = {
        @Authorization(value = "ApiKeyAuth")    }, tags={ "Users","Customer operation", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User account has successfully been updated."),
        @ApiResponse(code = 400, message = "Bad Request."),
        @ApiResponse(code = 401, message = "You are authorized to change user information."),
        @ApiResponse(code = 403, message = "You do not have the right function to update a user, please contact your employer."),
        @ApiResponse(code = 404, message = "Something went wrong with your request."),
        @ApiResponse(code = 406, message = "Invalid input, double check the values of the input fields and try again."),
        @ApiResponse(code = 429, message = "You are tried too many times to update a user, please wait a minute before you try again.") })
    @RequestMapping(value = "/user/{userId}",
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateUserById(@ApiParam(value = "" ,required=true )  @Valid @RequestBody User body
,@ApiParam(value = "The id from the user",required=true) @PathVariable("userId") Integer userId
);

}
