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
        if(FindUserByEmail(user.getEmail()) != null)
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

        userRepository.deleteUserById(userId);
        return 201;
    }

    public Integer UpdateUserById(User u)
    {
        userRepository.updateUser(u);
        return 201;
    }

}