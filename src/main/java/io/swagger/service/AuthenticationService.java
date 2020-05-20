package io.swagger.service;

import io.swagger.model.*;
import io.swagger.repository.AuthTokenRepository;
import io.swagger.repository.RegisterRequestRepository;
import io.swagger.repository.UserRepository;
import jdk.nashorn.internal.parser.Token;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class AuthenticationService {
    private AuthTokenRepository authTokenRepository;
    private UserRepository userRepository;
    private RegisterRequestRepository registerRequestRepository;
    private UserService userService;
    private MessageDigest md;

    public AuthenticationService(AuthTokenRepository authTokenRepository, UserRepository userRepository, RegisterRequestRepository registerRequestRepository, UserService userService) {
        this.authTokenRepository = authTokenRepository;
        this.userRepository = userRepository;
        this.registerRequestRepository = registerRequestRepository;
        this.userService = userService;
    }

    public RegisterRequest CreateRegisterRequest(RegisterRequest registerRequest)
    {
        //if user already reguested
        if(registerRequestRepository.findUserByEmail(registerRequest.getEmail()) != null)
            return registerRequest;
        else if(!registerRequest.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
            return null;
        else if(!userService.isValidPassword(registerRequest.getPassword()))
            return null;

        registerRequest.setPassword(cryptWithMD5(registerRequest.getPassword()));
        registerRequestRepository.save(registerRequest);
        return registerRequest;
    }
    public int DeleteRegisterRequest(int requestId)
    {
        if(!registerRequestRepository.existsById(requestId))
            return 406;
        registerRequestRepository.deleteById(requestId);
        return 200;
    }

    public Integer SignOutUser(String authToken)
    {

        if(!authTokenRepository.existsById(authToken))
            return 406;
        authTokenRepository.deleteById(authToken);
        return 200;
    }


    public boolean IsUserAuthenticated(String token, int userId, boolean isEmployeeRequest)
    {
        //token exist
        if(!authTokenRepository.existsById(token))
            return false;

        AuthToken authToken = authTokenRepository.findById(token).get();
        User.UserTypeEnum userType = userRepository.findById(authToken.getUserId()).get().getUserType();

        if(isEmployeeRequest && (userType == User.UserTypeEnum.CUSTOMER))
            return false;

        //if user in path given check if user connected to token is requesting
        if(userId != 0) {
            //employee requested
            if (userType == User.UserTypeEnum.EMPLOYEE || userType == User.UserTypeEnum.CUSTOMERANDEMPLOYEE)
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
        User user;
        if((user = userRepository.findUserByUserCredentials(login.getUsername(), cryptWithMD5(login.getPassword()))) == null)
            return null;
        AuthToken authToken = authTokenRepository.findAuthTokenByUser(user.getUserId());
        //user already has token
        if(authToken != null) {
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

    public String cryptWithMD5(String pass){
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            String hashedPassword = "";
            for(int i = 0; i < 25; i++)
            {
                hashedPassword += sb.charAt(i);
            }
            return hashedPassword;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;


    }
}
