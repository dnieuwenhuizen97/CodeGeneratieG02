package io.swagger.service;

import io.swagger.model.*;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.AuthTokenRepository;
import io.swagger.repository.UserRepository;
import org.springframework.stereotype.Service;
import io.swagger.repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TransactionService {
    TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private AuthTokenRepository authTokenRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, AccountRepository accountRepository, AuthTokenRepository authTokenRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository =  userRepository;
        this.accountRepository = accountRepository;
        this.authTokenRepository = authTokenRepository;
    }

    public List<Transaction> getAllTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    public List<Transaction> getAllTransactionsOfUser(Integer userId, String token) throws Exception {
        //User id must be provided
        if(userId == null){
            throw new Exception("The user id you entered is wrong");
        }
        //Find performing user
        User user = getUserById(userId);

        //Find logged-in user
        User loggedInUser = getLoggedInUser(token);

        validateUserDetails(loggedInUser, user);

        List<Account> accounts = accountRepository.findAccountByOwner(user.getUserId());

        //Retrieve all transaction details of the user
        Set<Transaction> allTransactionSet = new HashSet<Transaction>();
        for(Account account : accounts){
            allTransactionSet.addAll(transactionRepository.findByIban(account.getIban()));
        }

        List<Transaction> allTransaction = new ArrayList<Transaction>(allTransactionSet);
        return allTransaction;
    }

    private void validateUserDetails(User loggedInUser, User user)throws Exception {

        // no user found for give userId
        if (user == null) {
            throw new Exception("The user id you entered is wrong");
        }

        // customer is only able to check it's own transaction
        if (loggedInUser.getUserType() == User.UserTypeEnum.CUSTOMER) {
            if (loggedInUser.getUserId().intValue() != user.getUserId().intValue()) {
                throw new Exception("You cannot search for all transactions");
            }
        }
    }

    public Transaction createMachineTransfer(int userId, MachineTransfer machineTransfer)
    {
        //withdraw remove from bank account and banks own
        //deposit add to bank account and banks own

        Transaction machineTransaction = new Transaction(machineTransfer.getTransferType().toString(), LocalDateTime.now(), "NL13INHO1234567890", "NL13INHO1234567890", machineTransfer.getAmount(), userId);
        transactionRepository.save(machineTransaction);
        return machineTransaction;
    }

    //to do geld ook echt overschrijven
    public Transaction createTransactionForUser(Transaction transaction, String token) throws Exception {
        //Find loggedIn user
        User loggedInUser = getLoggedInUser(token);

        //use current time if not provided in the input attribute
        if(transaction.getTimestamp() == null){
            transaction.setTimestamp(LocalDateTime.now());
        }
        //use default transaction if not provided in the input attribute
        if(transaction.getTransactionType() == null){
            transaction.setTransactionType(Transaction.TransactionTypeEnum.TRANSACTION);
        }
        //use logged-in userId if performing user is not provided in the input attribute
        if(transaction.getUserPerforming() == null){
            transaction.setUserPerforming(loggedInUser.getUserId());
        }

        //Validate the provided input before creating transaction
        validateInput(transaction, loggedInUser);


        return transactionRepository.save(transaction);
    }



    /**
     * Validate the provided input to perform transaction
     * @param transaction
     * @param loggedInUser
     * @throws Exception
     */
    private void validateInput(Transaction transaction, User loggedInUser) throws Exception {

        User user = getUserById(transaction.getUserPerforming());
        //Check if user is a valid user
        if(user == null){
            throw new Exception("The user your are performing is does not exists");
        }

        Account accountFrom = getAccountById(transaction.getAccountFrom());
        Account accountTo = getAccountById(transaction.getAccountTo());
        //Check if account from is a valid account
        if(accountFrom == null){
            throw new Exception("The account your sending money from does not exists");
        }
        //Check if account to is a valid account
        else if(accountTo == null){
            throw new Exception("The account your sending money to does not exists");
        }

        //Check for customer
        if(loggedInUser.getUserType() == User.UserTypeEnum.CUSTOMER ){
            //First two checks: user is only able to transfer if it's the same userId or type is employee
            if(user.getUserId() != loggedInUser.getUserId()){
                throw new Exception("You are trying to perform a transaction on someone else !");
            }
            //AND the sender is sending money with his own IBAN
            else if(accountFrom.getOwner().intValue() != loggedInUser.getUserId().intValue() ){
                throw new Exception("You are trying to perform a transaction on someone else account !");
            }
        }

        //Unable to transfer to own account (Account to is the same as account from)
        else if(transaction.getAccountFrom().equals(transaction.getAccountTo())){
            throw new Exception("You cannot transfer to your own account!");
        }
        //Unable to transfer funds to another savings (besides your own)
        else if (accountFrom.getAccountType() == Account.AccountTypeEnum.SAVINGS && user.getUserType() != User.UserTypeEnum.CUSTOMER){
            throw new Exception("You cannot transfer the funds to a savings account.");
        }

        //Check if amount is higher than 0
        if(transaction.getAmount() < 0){
            throw new Exception("You cannot transfer a negative number.");
        }
        //Customer cannot transfer 0 (nothing)
        else if(transaction.getAmount() == 0){
            throw new Exception("You cannot transfer nothing.");
        }
        //Needs to be changed to the absolute limit
        else if(accountFrom.getBalanceLimit().doubleValue() < transaction.getAmount()){
            throw new Exception("Your have extended your absolute limit, please deposit money first.");
        }
        //Check is transfer is higher than balance
        else if(accountFrom.getBalance() < transaction.getAmount()){
            throw new Exception("You do not have enough balance to transfer this amount!");
        }
        //A user has a maximum of transactions per day
        else if(accountFrom.getTransactionDayLimit() <= getTodaysTransactionCount(user)){
            throw new Exception(String.format("You have reached your day limit of %d transactions.",accountFrom.getTransactionDayLimit()));
        }
        //Amount of transactions per day
        else if((getTodaysTransactionAmount(user) + transaction.getAmount()) >= accountFrom.getTransactionAmountLimit().doubleValue()){
            throw new Exception("You have reached your transaction limit, please wait until tomorrow.");
        }
    }

    /**Get all transaction done by a User on same day
     * @param user
     * @return
     */
    List<Transaction> getAllTodaysTransactions(User user) {

        List<Account> accounts = accountRepository.findAccountByOwner(user.getUserId());

        // Retrieve all transaction details of the user
        Set<Transaction> allTransactionSet = new HashSet<Transaction>();
        for (Account account : accounts) {
            allTransactionSet.addAll(transactionRepository.findByIban(account.getIban()));
        }

        List<Transaction> todaysTransaction = new ArrayList<Transaction>();
        LocalDateTime timeNow = LocalDateTime.now();
        for (Transaction trans : allTransactionSet) {
            if (trans.getTimestamp().getYear() == timeNow.getYear()
                    && trans.getTimestamp().getMonth() == timeNow.getMonth()
                    && trans.getTimestamp().getDayOfMonth() == timeNow.getDayOfMonth()) {

                todaysTransaction.add(trans);
            }
        }

        return todaysTransaction;
    }

    /**returns total amount of transaction done today for a user
     * @param user
     * @return
     */
    private Double getTodaysTransactionAmount(User user) {

        Double totalTransactionAmount = new Double(0);
        List<Transaction> todaysTransaction = getAllTodaysTransactions(user);
        for (Transaction trans: todaysTransaction){

            totalTransactionAmount += trans.getAmount();
        }
        return totalTransactionAmount;
    }

    /**returns number of transaction done today for a user
     * @param user
     * @return
     */
    private Integer getTodaysTransactionCount(User user) {

        List<Transaction> todaysTransaction = getAllTodaysTransactions(user);

        return todaysTransaction.size();
    }
    /**
     * get logged-in user from authentication token
     * @param token
     * @return
     */

    private User getLoggedInUser(String token) {
        AuthToken authToken = authTokenRepository.findById(token).get();
        Integer loggedInUserId = authToken.getUserId();
        User loggedInUser= getUserById(loggedInUserId);
        return loggedInUser;
    }

    /**
     * Find a User by it's id
     * @param userId
     * @return user
     */
    private User getUserById(Integer userId) {
        //Check if user exist or not
        if (!userRepository.findById(userId).isPresent()) { return null; }

        return userRepository.findById(userId).get();
    }
    /**
     * Find a Account by it's id
     *
     * @param accountId
     * @return account
     */
    private Account getAccountById(String accountId) {
        //Check if account exist or not
        if (!accountRepository.findById(accountId).isPresent()) {  return null;  }

        return accountRepository.findById(accountId).get();
    }
}
