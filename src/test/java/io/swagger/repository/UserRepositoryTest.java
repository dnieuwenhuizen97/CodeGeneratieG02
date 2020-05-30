package io.swagger.repository;

import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.BeforeEach;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest

public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserByExistingUserCredentialsShouldReturnNotNull() throws Exception
    {
        User user = userRepository.findUserByUserCredentials("employee", "5f4dcc3b5aa765d61d8327deb");
        assertNotNull(user);
    }

    @Test
    public void findUserByNotExistingUserCredentialsShouldReturnNull() throws Exception
    {
        User user = userRepository.findUserByUserCredentials("notExisting", "password");
        assertNull(user);
    }

    @Test
    public void findUserByExistingEmailShouldNotReturnNull() throws Exception {
        assertNotNull(userRepository.findUserByEmail("testuser@gmail.com"));
    }

    @Test
    public void findUserByNonExistingEmailShouldReturnNull() throws Exception {
        assertNull(userRepository.findUserByEmail("nonexisting@gmai.com"));
    }

    @Test
    public void findUserByExistingLastNameShouldNotReturnNull() throws Exception {
        assertNotNull(userRepository.findUsersByLastName("user"));
    }

    @Test
    public void findUsersByNonExistingLastNameShouldReturnEmptyList() throws Exception {
        List<User> users = (userRepository.findUsersByLastName("nonExistingLastName"));
        assertTrue(users.size() == 0);
    }

    @Test
    public void updateUserByExistingIdShouldSuccessfullyUpdateUser() throws Exception {
        userRepository.updateUser("John", "Doe", "johndoe@gmail.com", "newPassword1!", 100054);
        assertTrue(userRepository.findUserByUserCredentials("johndoe@gmail.com", "newPassword1!") != null);
    }

    @Test
    public void updateUserByNonExistingIdShouldFailToUpdateUser() throws Exception {
        userRepository.updateUser("John", "Doe", "otheremail@gmail.com", "newPassword1!", 00000);
        assertTrue(userRepository.findUserByUserCredentials("otheremail@gmail.com", "newPassword1!") == null);
    }

}