package io.swagger.api;

import io.swagger.model.User;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith (SpringRunner.class)
@SpringBootTest
public class UsersApiTest {

    @Autowired
    private UsersApi usersApi;

    @Test
    public void createUserTest() throws Exception {
        String firstName = "first_name_example";
        String lastName = "last_name_example";
        String password = "password_example";
        String email = "example@test.mail";
        String userType = "customer";
        User body = new User();

        ResponseEntity<User> responseEntity = usersApi.createUser(body);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void getAllUsersTest() throws Exception {
        ResponseEntity<List<User>> responseEntity = usersApi.getAllUsers(0, 10, "employee", "employee");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        Integer userId = 100052;
        ResponseEntity<User> responseEntity = usersApi.getUserById(userId);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
