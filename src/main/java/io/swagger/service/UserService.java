package io.swagger.service;

import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Integer SignUpUser(User user)
    {
        //user already exist
        if(findUserByEmail(user.getEmail()) != null)
            return 406;

        //to do: valid email check, valid password check

        userRepository.save(user);
        return 201;
    }

    private User findUserByEmail(String email)
    {
        return userRepository.findUserByEmail(email);
    }

    public List<User> getAllUsers() {
        return (List<User>)userRepository.findAll();
    }

    public void createUser(User u) {
        userRepository.save(u);
    }

    public void deleteUser(User u) {
        userRepository.delete(u);
    }

}