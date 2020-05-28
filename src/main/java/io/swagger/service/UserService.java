package io.swagger.service;

import io.swagger.model.RegisterRequest;
import io.swagger.model.User;
import io.swagger.repository.RegisterRequestRepository;
import io.swagger.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.security.SecureRandom;

@Service
public class UserService {

    private UserRepository userRepository;
    private RegisterRequestRepository registerRequestRepository;

    public UserService (UserRepository userRepository, RegisterRequestRepository registerRequestRepository) {
        this.userRepository = userRepository;
        this.registerRequestRepository = registerRequestRepository;

    }
    public List<RegisterRequest> FindAllRegisterRequests(){return (List<RegisterRequest>) registerRequestRepository.findAll();};

    public User SignUpUser(User user) throws Exception {
        if (FindUserByEmail(user.getEmail()) != null)
            throw new Exception("User already exists");
        else if (!user.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
            throw new Exception("Invalid email");
        else if (!isValidPassword(user.getPassword()))
            throw new Exception("Password needs to be 8-15 characters long and should contain at least ONE digit, ONE special character and ONE uppercase letter");

        userRepository.save(user);
        return user;
    }


    public User FindUserById(int userId)
    {
        return userRepository.findById(userId).get();
    }

    public User FindUserByEmail(String email)
    {
        return userRepository.findUserByEmail(email);
    }

    public List<User> FindUserByName(String name) {return userRepository.findUsersByLastName(name.toLowerCase()); }

    public List<User> GetAllUsers() {
        return (List<User>)userRepository.findAll();
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
        else if (!isValidPassword(u.getPassword()))
            throw new Exception("Password needs to be 8-15 characters long and should contain at least ONE digit, ONE special character and ONE uppercase letter");
        else if (!u.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
            throw new Exception("Invalid email");

        userRepository.updateUser(u.getFirstName(), u.getLastName(), u.getEmail(), u.getPassword(), userId);

        User updatedUser = userRepository.findById(userId).get();
        return updatedUser;
    }

    private boolean isValidPassword(String password)
    {
        // for checking if password length
        // is between 8 and 15
        if (!((password.length() >= 8)
                && (password.length() <= 15))) {
            return false;
        }

        // to check space
        if (password.contains(" ")) {
            return false;
        }
        if (true) {
            int count = 0;

            // check digits from 0 to 9
            for (int i = 0; i <= 9; i++) {

                // to convert int to string
                String str1 = Integer.toString(i);

                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }

        // for special characters
        if (!(password.contains("@") || password.contains("#")
                || password.contains("!") || password.contains("~")
                || password.contains("$") || password.contains("%")
                || password.contains("^") || password.contains("&")
                || password.contains("*") || password.contains("(")
                || password.contains(")") || password.contains("-")
                || password.contains("+") || password.contains("/")
                || password.contains(":") || password.contains(".")
                || password.contains(", ") || password.contains("<")
                || password.contains(">") || password.contains("?")
                || password.contains("|"))) {
            return false;
        }

        if (true) {
            int count = 0;

            // checking capital letters
            for (int i = 65; i <= 90; i++) {

                // type casting
                char c = (char) i;

                String str1 = Character.toString(c);
                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }

        if (true) {
            int count = 0;

            // checking small letters
            for (int i = 90; i <= 122; i++) {

                // type casting
                char c = (char) i;
                String str1 = Character.toString(c);

                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }

        return true;
    }


}