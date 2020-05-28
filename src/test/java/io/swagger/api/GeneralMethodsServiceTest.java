package io.swagger.api;

import io.swagger.service.GeneralMethodsService;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.TestCase.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneralMethodsServiceTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    private  GeneralMethodsService generalMethodsService;

    @Before
    public void setUp() { this.generalMethodsService = new GeneralMethodsService();}


    @Test
    public void checkIfPasswordIsHashedWithHashedPasswordShouldReturnTrue() throws Exception
    {

        String hashedPassword = "5f4dcc3b5aa765d61d8327deb";
        assertEquals(true ,generalMethodsService.isPasswordHashed(hashedPassword));
        //assertTrue();
    }

    @Test
    public void checkIfPasswordIsHashedWithNotHashedPasswordShouldReturnFalse() throws Exception
    {
        String password = "unhashedpassword";
        assertFalse(generalMethodsService.isPasswordHashed(password));
    }

    @Test
    public void encryptPasswordWithMD5ShouldReturnEncryptedPassword() throws Exception
    {
        String password = "unhashedpassword";
        String hashedPassword = generalMethodsService.cryptWithMD5(password);

        assertEquals(true ,generalMethodsService.isPasswordHashed(hashedPassword));
    }

    @Test
    public void checkIfPasswordIsValidWithValidPasswordShouldReturnTrue() throws Exception
    {
        String validPassword = "Password123!";
        assertEquals(true ,generalMethodsService.isValidPassword(validPassword));
    }

    @Test
    public void checkIfPasswordIsValidWithInvalidPasswordShouldReturnFalse() throws Exception
    {
        String invalidPassword = "notValidPassword";
        assertEquals(false ,generalMethodsService.isValidPassword(invalidPassword));
    }
}
