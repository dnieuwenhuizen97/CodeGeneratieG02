package io.swagger.service;

import io.swagger.model.RegisterRequest;
import io.swagger.model.User;
import io.swagger.repository.RegisterRequestRepository;
import io.swagger.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private RegisterRequestRepository registerRequestRepository;

    public UserService (UserRepository userRepository, RegisterRequestRepository registerRequestRepository) {
        this.userRepository = userRepository;
        this.registerRequestRepository = registerRequestRepository;

    }
    public List<RegisterRequest> FindAllRegisterRequests(){return (List<RegisterRequest>) registerRequestRepository.findAll();};
    public Integer SignUpUser(User user)
    {
        //user already exist
        if (FindUserByEmail(user.getEmail()) != null || !user.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")
            || !ValidPasswordCheck(user.getPassword()))
            return 406;

        //to do: valid email check, valid password check

        userRepository.save(user);
        return 201;
    }
    public User FindUserById(int userId)
    {
        return userRepository.findUserById(userId);
    }

    public User FindUserByEmail(String email)
    {
        return userRepository.findUserByEmail(email);
    }

    public List<User> GetAllUsers() {
        return (List<User>)userRepository.findAll();
    }

    public Integer DeleteUserById(int userId)
    {
        if (!userRepository.existsById(userId))
            return 406;

        userRepository.deleteById(userId);
        return 201;
    }

    public Integer UpdateUserById(User u)
    {
        User originalUser = userRepository.findUserById(u.getUserId());

        if (!userRepository.existsById(u.getUserId()))
            return 406;
        else if (originalUser.getUserType().toString() != u.getUserType().toString())
            return 401;

        userRepository.save(u);
        return 200;
    }

    public boolean ValidPasswordCheck(String password)
    {
        if (!((password.length() >= 8) && (password.length() <= 15)))
            return false;
        else if (password.contains(" "))
            return false;
        else if (true) {
            int count = 0;

            for (int i = 0; i <= 9; i++) {
                String s = Integer.toString(i);

                if (password.contains(s))
                    count = 1;
            }
            if (count == 0)
                return false;
        }
        else if (!(password.contains("@") || password.contains("#")
                || password.contains("!") || password.contains("~")
                || password.contains("$") || password.contains("%")
                || password.contains("^") || password.contains("&")
                || password.contains("*") || password.contains("(")
                || password.contains(")") || password.contains("-")
                || password.contains("+") || password.contains("/")
                || password.contains(":") || password.contains(".")
                || password.contains(", ") || password.contains("<")
                || password.contains(">") || password.contains("?")
                || password.contains("|")))
            return false;
        else if (true) {
            int count = 0;

            for (int i = 65; i <= 90; i++) {
                char c = (char)i;

                String s = Character.toString(c);
                if (password.contains(s))
                    count = 1;
            }
            if (count == 0)
                return false;
        }

        return true;
    }

}