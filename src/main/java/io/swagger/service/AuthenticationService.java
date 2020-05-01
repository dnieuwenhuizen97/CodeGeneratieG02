package io.swagger.service;

import io.swagger.model.AuthToken;
import io.swagger.model.Login;
import io.swagger.repository.AuthTokenRepository;
import jdk.nashorn.internal.parser.Token;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthenticationService {
    private AuthTokenRepository authTokenRepository;

    public AuthenticationService(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;

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
        //TO-DO:user repo check if user and return userId
        int userId = 1;

        if(userId != 0)
        {
            authToken = new AuthToken(CreateAuthToken(), userId, LocalDateTime.now());
            authTokenRepository.save(authToken);
        }
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
