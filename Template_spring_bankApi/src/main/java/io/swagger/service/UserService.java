package io.swagger.service;

import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository repository;

    public UserService (UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return (List<User>)repository.findAll();
    }

    public void createUser(User u) {
        repository.save(u);
    }

    public void deleteUser(User u) {
        repository.delete(u);
    }

}
