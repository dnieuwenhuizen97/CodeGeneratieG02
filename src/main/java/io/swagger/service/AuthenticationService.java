package io.swagger.service;

import io.swagger.model.*;
import io.swagger.repository.AuthTokenRepository;
import io.swagger.repository.RegisterRequestRepository;
import io.swagger.repository.UserRepository;
import jdk.nashorn.internal.parser.Token;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class AuthenticationService {
    private AuthTokenRepository authTokenRepository;
    private UserRepository userRepository;
    private RegisterRequestRepository registerRequestRepository;

    public AuthenticationService(AuthTokenRepository authTokenRepository, UserRepository userRepository, RegisterRequestRepository registerRequestRepository) {
        this.authTokenRepository = authTokenRepository;
        this.userRepository = userRepository;
        this.registerRequestRepository = registerRequestRepository;
    }

    public Integer CreateRegisterRequest(RegisterRequest registerRequest)
    {
        //if user already reguested
        if(registerRequestRepository.findUserByEmail(registerRequest.getEmail()) != null)
            return  406;

        registerRequestRepository.save(registerRequest);
        return 201;
    }

    public Integer SignOutUser(String authToken)
    {
        authTokenRepository.deleteById(authToken);
        return 200;
    }


    public boolean IsUserAuthenticated(String token, int userId, boolean isEmployeeRequest)
    {
        Optional<AuthToken> returnedToken = authTokenRepository.findById(token);
        AuthToken authToken = returnedToken.get();
        //token exist
        if(authToken == null)
            return false;

        if(isEmployeeRequest && (userRepository.findById(authToken.getUserId()).get().getUserType()) == User.UserTypeEnum.CUSTOMER)
            return false;

        //if user in path given check if user connected to token is requesting
        if(userId != 0) {
            //employee requested
            if (userRepository.findById(authToken.getUserId()).get().getUserType() == User.UserTypeEnum.EMPLOYEE || userRepository.findById(authToken.getUserId()).get().getUserType() == User.UserTypeEnum.CUSTOMERANDEMPLOYEE)
                return true;
            //customer requested
            else if(userId == authToken.getUserId())
                return true;
            else
                return false;
        }
        else
            return true;
    }

    public AuthToken ValidateUserAndReturnAuthToken(Login login)
    {
        User user = userRepository.findUserByUserCredentials(login.getUsername(), login.getPassword());
        AuthToken authToken = authTokenRepository.findAuthTokenByUser(user.getUserId());

        //no user found with credentials
        if(user == null)
            return authToken;
        //user already has token
        else if(authToken != null) {
            return authToken;
        }

        //token will expire after 30min from now
        authToken = new AuthToken(CreateAuthToken(), user.getUserId(), LocalDateTime.now(), LocalDateTime.now().plusMinutes(30));
        authTokenRepository.save(authToken);

        return authToken;
    }
    private String CreateAuthToken()
    {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        String token = "";

        List<String> tokenParts = new ArrayList<>();
        tokenParts.add("");
        tokenParts.add("");
        tokenParts.add("");
        tokenParts.add("");

        int partCounter = 1;
        for(String part : tokenParts) {
            for (int i = 1; i < 5; i++) {
                if(partCounter % 2 == 1 )
                    part += Integer.toString(rand.nextInt(10));
                else
                    part += alphabet.charAt(rand.nextInt(alphabet.length()));
                if(i == 4) {
                    token += "-" + part;
                    partCounter++;
                }

            }
        }
        //remove first -
        token = token.replaceFirst("-", "");
        return token;
    }
}
