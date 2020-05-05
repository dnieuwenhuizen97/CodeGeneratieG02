package io.swagger.controller;

import io.swagger.api.UsersApi;
import io.swagger.model.RegisterRequest;
import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.service.AuthenticationService;
import io.swagger.service.UserService;
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
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-28T15:21:59.457Z[GMT]")
@Controller
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private AuthenticationService authService;
    private UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request, AuthenticationService authService, UserService userService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.authService = authService;
        this.userService = userService;

        userService.SignUpUser(new User("Dylan", "Nieuwenhuizen", "TestPassword", "dylan@test.nl", "employee"));
        userService.SignUpUser(new User("fgf", "sdssf", "hdfv", "sgbdfsg@test.nl", "customer"));

        System.out.println("\nTHIS IS A GET ALL:");
        for (User u : userService.GetAllUsers())
            System.out.println(u);

        System.out.println("\nTHIS IS A GET BY EMAIL:\n" + userService.FindUserByEmail("dylan@test.nl"));
        System.out.println("\nTHIS IS A GET BY ID:\n" + userService.FindUserById(100002));
    }

    public ResponseEntity<Void> createUser(@ApiParam(value = "Created user object" ,required=true )  @Valid @RequestBody User body
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, 0))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        //make one for user request aceeptance---only add role
        //make one for cerating user from scratch

        return new ResponseEntity<Void>(HttpStatus.valueOf(userService.SignUpUser(body)));
    }

    public ResponseEntity<List<RegisterRequest>> getAllRegisterRequests() {
        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, 0))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<RegisterRequest>>(objectMapper.readValue("[ {\n  \"firstName\" : \"firstName\",\n  \"lastName\" : \"lastName\",\n  \"password\" : \"\",\n  \"email\" : \"email\"\n}, {\n  \"firstName\" : \"firstName\",\n  \"lastName\" : \"lastName\",\n  \"password\" : \"\",\n  \"email\" : \"email\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<RegisterRequest>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<List<RegisterRequest>>(userService.FindAllRegisterRequests(), HttpStatus.OK);
    }

    public ResponseEntity<List<User>> getAllUsers(@ApiParam(value = "The number of items to skip before starting to collect the result set") @Valid @RequestParam(value = "offset", required = false) Integer offset
,@ApiParam(value = "The numbers of items to return") @Valid @RequestParam(value = "limit", required = false) Integer limit
,@ApiParam(value = "The name the user should have") @Valid @RequestParam(value = "name", required = false) String name
,@ApiParam(value = "The iban the user should have") @Valid @RequestParam(value = "iban", required = false) String iban
,@ApiParam(value = "The id the user should have") @Valid @RequestParam(value = "userId", required = false) Integer userId
,@ApiParam(value = "The email the user should have") @Valid @RequestParam(value = "email", required = false) String email
) {
        String accept = request.getHeader("Accept");

        String apiKeyAuth = request.getHeader("ApiKeyAuth");
        if(!authService.IsUserAuthenticated(apiKeyAuth, 0))
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<User>>(objectMapper.readValue("[ {\n  \"firstName\" : \"John\",\n  \"lastName\" : \"van Vuuren\",\n  \"password\" : \"thisismypassword3485\",\n  \"user_type\" : [ \"customer\", \"customer\" ],\n  \"user_id\" : 1,\n  \"email\" : \"john@vanVuuren.com\"\n}, {\n  \"firstName\" : \"John\",\n  \"lastName\" : \"van Vuuren\",\n  \"password\" : \"thisismypassword3485\",\n  \"user_type\" : [ \"customer\", \"customer\" ],\n  \"user_id\" : 1,\n  \"email\" : \"john@vanVuuren.com\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<User>>(userService.GetAllUsers(), HttpStatus.OK);
    }

}
