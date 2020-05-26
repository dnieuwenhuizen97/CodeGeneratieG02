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

<<<<<<< Updated upstream
        //to do: valid email check, valid password check
=======
    public User SignUpUser(User user) throws Exception {
        if (FindUserByEmail(user.getEmail()) != null)
            throw new Exception("User already exists");
        else if (!user.getEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
            throw new Exception("Invalid email");
        else if (!isValidPassword(user.getPassword()))
            throw new Exception("Password needs to be 8-15 characters long and should contain at least ONE digit, ONE special character and ONE uppercase letter");
>>>>>>> Stashed changes

        userRepository.save(user);
        return 201;
    }
    public User FindUserById(int userId)
    {
        return userRepository.findById(userId).get();
    }

    private User FindUserByEmail(String email)
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