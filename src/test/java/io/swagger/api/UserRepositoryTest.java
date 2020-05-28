package io.swagger.api;


import io.swagger.model.User;
import io.swagger.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest

public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;


    @Before
    public void setUp() {  }

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
}
