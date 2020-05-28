package io.swagger.model;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        user = new User("John", "Doe", "Password1!", "johndoe@gmail.com", "customer");
    }

    @Test
    public void creatingNewUserShouldNotBeNull() throws Exception {
        assertNotNull(user);
    }

}