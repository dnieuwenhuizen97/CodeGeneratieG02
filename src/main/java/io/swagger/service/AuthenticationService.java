package io.swagger.service;

import io.swagger.model.AuthToken;
import io.swagger.model.Login;
import io.swagger.model.User;
import io.swagger.repository.AuthTokenRepository;
import io.swagger.repository.UserRepository;
import jdk.nashorn.internal.parser.Token;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthenticationService {
    private AuthTokenRepository authTokenRepository;
    private UserRepository userRepository;

    public AuthenticationService(AuthTokenRepository authTokenRepository, UserRepository userRepository) {
        this.authTokenRepository = authTokenRepository;
        this.userRepository = userRepository;
    }

    public boolean IsUserAuthenticated(String token)
    {
      AuthToken authToken = authTokenRepository.findAuthTokenByToken(token);
        if(authToken != null)
            return true;
        return false;
    }

    public AuthToken ValidateUserAndReturnAuthToken(Login login)
    {
        AuthToken authToken = null;
        User user = userRepository.findUserByUserCredentials(login.getUsername(), login.getPassword());

        if(user == null)
            return authToken;

        authToken = new AuthToken(CreateAuthToken(), user.getUserId(), LocalDateTime.now());
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
