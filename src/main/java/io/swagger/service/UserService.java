package io.swagger.service;

import io.swagger.model.RegisterRequest;
import io.swagger.model.User;
import io.swagger.repository.RegisterRequestRepository;
import io.swagger.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@Service
public class UserService {

    private UserRepository userRepository;
    private RegisterRequestRepository registerRequestRepository;
    private GeneralMethodsService generalMethodsService;


    public UserService (UserRepository userRepository, RegisterRequestRepository registerRequestRepository, GeneralMethodsService generalMethodsService) {
        this.userRepository = userRepository;
        this.registerRequestRepository = registerRequestRepository;
        this.generalMethodsService = generalMethodsService;

    }
    public List<RegisterRequest> FindAllRegisterRequests(){return (List<RegisterRequest>) registerRequestRepository.findAll();};

    public User SignUpUser(User user) throws Exception {
        //register request will give an hashed password
        if(generalMethodsService.isPasswordHashed(user.getPassword())) {
            userRepository.save(user);
            return user;
        }
        else if (FindUserByEmail(user.getEmail()) != null)
            throw new Exception("User already exists");
        else if (!user.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
            throw new Exception("Invalid email");
        else if (!generalMethodsService.isValidPassword(user.getPassword()))
            throw new Exception("Password needs to be 8-15 characters long and should contain at least ONE digit, ONE special character and ONE uppercase letter");

        //encrypt password
        user.setPassword(generalMethodsService.cryptWithMD5(user.getPassword()));
        userRepository.save(user);
        return user;
    }


    public User FindUserById(int userId)
    {
        //to do user bestaat niet

        return userRepository.findById(userId).get();
    }


    private User FindUserByEmail(String email)
    {
        return userRepository.findUserByEmail(email);
    }

    public List<User> GetAllUsers() {
        return (List<User>)userRepository.findAll();
    }


    public void createUser(User u) {
        userRepository.save(u);
    }

    public Integer DeleteUserById(int userId)
    {
        if (!userRepository.existsById(userId))
            return 406;

        userRepository.deleteById(userId);
        return 204;
    }

    public User UpdateUserById(User u, int userId) throws Exception {

        if (!userRepository.existsById(userId))
            throw new Exception("User does not exist");
        else if (!generalMethodsService.isValidPassword(u.getPassword()))
            throw new Exception("Password needs to be 8-15 characters long and should contain at least ONE digit, ONE special character and ONE uppercase letter");
        else if (!u.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
            throw new Exception("Invalid email");

        userRepository.updateUser(u.getFirstName(), u.getLastName(), u.getEmail(), u.getPassword(), userId);

        User updatedUser = userRepository.findById(userId).get();
        return updatedUser;
    }





}