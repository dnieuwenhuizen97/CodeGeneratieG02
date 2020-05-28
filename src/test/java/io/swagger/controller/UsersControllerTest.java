package io.swagger.controller;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(webApplicationContext).build();
    }

    //Get All Users
    @Test
    public void getAllUsersWithValidTokenAndValidRoleShouldReturnOk() throws Exception {
         mvc.perform(get("/users")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUsersWithInvalidTokenShouldReturnForbidden() throws Exception {
        mvc.perform(get("/users")
                .header("ApiKeyAuth", "InvalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllUsersWithValidTokenAndInValidRoleShouldReturnForbidden() throws Exception {
        mvc.perform(get("/users")
                .header("ApiKeyAuth", "1111-abcd-5678-efgh"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllUsersFilteredByEmailWithNonexistingEmailShouldReturnNotAcceptable() throws Exception {
        mvc.perform(get("/users?email=invalidemail")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void getAllUsersFilteredByEmailShouldReturnOk() throws Exception {
        mvc.perform(get("/users?email=customer")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUsersFilteredByNameShouldReturnOk() throws Exception {
        mvc.perform(get("/users?name=customer")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isOk());
    }

    //Get User By ID
    @Test
    public void getUserByIdWithValidTokenAndValidRoleShouldReturnOk() throws Exception {
        mvc.perform(get("/users/100052")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByIdWithValidTokenAndInvalidRoleShouldReturnForbidden() throws Exception {
        mvc.perform(get("/users/100052")
                .header("ApiKeyAuth", "1111-abcd-5678-efgh"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getUserByIdWithInvalidTokenShouldReturnForbidden() throws Exception {
        mvc.perform(get("/users/100052")
                .header("ApiKeyAuth", "InvalidToken"))
                .andExpect(status().isForbidden());
    }

    //Create New User
    @Test
    public void createUserWithEmployeeTokenAndValidInputShouldReturnCreated() throws Exception {

        JSONObject createUser = new JSONObject();
        createUser.put("firstName", "John");
        createUser.put("lastName", "Doe");
        createUser.put("password", "Password1!");
        createUser.put("email", "johndoe@gmail.com");
        createUser.put("user_type", "customer");

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(createUser.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "    'user_id': 100055,\n" +
                        "    'firstName': 'John',\n" +
                        "    'lastName': 'Doe',\n" +
                        "    'password': 'Password1!',\n" +
                        "    'email': 'johndoe@gmail.com',\n" +
                        "    'user_type': 'customer'\n" +
                        "}"));
    }

    @Test
    public void createUserWithoutTokenAndValidInputShouldCreateRequestAndReturnCreated() throws Exception {

        JSONObject createUser = new JSONObject();
        createUser.put("firstName", "John");
        createUser.put("lastName", "Doe");
        createUser.put("password", "Password1!");
        createUser.put("email", "johndoe@gmail.com");
        createUser.put("user_type", "customer");

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(createUser.toString())
                .header("ApiKeyAuth", ""))
                .andExpect(status().isCreated());
    }

    @Test
    public void createUserWithoutTokenAndInvalidInputShouldTryToCreateRequestAndReturnNotAcceptable() throws Exception {

        JSONObject createUser = new JSONObject();
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(createUser.toString())
                .header("ApiKeyAuth", ""))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void createUserWithoutRequiredInputShouldReturnNotAcceptable() throws Exception {

        JSONObject createUser = new JSONObject();

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(createUser.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void createUserWithInvalidPassWordFormatShouldReturnNotAcceptable() throws Exception {

        JSONObject createUser = new JSONObject();
        createUser.put("firstName", "John");
        createUser.put("lastName", "Doe");
        createUser.put("password", "password");
        createUser.put("email", "johndoe@gmail.com");
        createUser.put("user_type", "customer");

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(createUser.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void createUserWithInvalidEmailFormatShouldReturnNotAcceptable() throws Exception {

        JSONObject createUser = new JSONObject();
        createUser.put("firstName", "John");
        createUser.put("lastName", "Doe");
        createUser.put("password", "Password1!");
        createUser.put("email", "johndoegmailcom");
        createUser.put("user_type", "customer");

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(createUser.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void createUserWithValidInputButUserAlreadyExistsShouldReturnNotAcceptable() throws Exception {

        JSONObject createUser = new JSONObject();
        createUser.put("firstName", "John");
        createUser.put("lastName", "Doe");
        createUser.put("password", "Password1!");
        createUser.put("email", "testuser@gmail.com");
        createUser.put("user_type", "customer");

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(createUser.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }

    //Delete User
    @Test
    public void deleteUserByIdWithValidTokenAndValidRoleShouldReturnNoContent() throws Exception {
        mvc.perform(delete("/users/100053")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteUserByIdWithValidTokenAndInvalidRoleShouldReturnForbidden() throws Exception {
        mvc.perform(delete("/users/100053")
                .header("ApiKeyAuth", "1111-abcd-5678-efgh"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteUserByIdWithInvalidTokenShouldReturnForbidden() throws Exception {
        mvc.perform(delete("/users/100053")
                .header("ApiKeyAuth", "InvalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteUserByIdWithNonExistingIdShouldReturnNotAcceptable() throws Exception {
        mvc.perform(delete("/users/0000")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }

    //Update User
    @Test
    public void updateUserByIdWithValidTokenAndValidRoleShouldReturnOk() throws Exception {

        JSONObject updateUser = new JSONObject();
        updateUser.put("firstName", "John");
        updateUser.put("lastName", "Doe");
        updateUser.put("password", "Password1!");
        updateUser.put("email", "johndoe@gmail.com");

        mvc.perform(put("/users/100052")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updateUser.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserByIdWithValidTokenAndInvalidRoleShouldReturnForbidden() throws Exception {

        JSONObject updateUser = new JSONObject();
        updateUser.put("firstName", "John");
        updateUser.put("lastName", "Doe");
        updateUser.put("password", "Password1!");
        updateUser.put("email", "johndoe@gmail.com");

        mvc.perform(put("/users/100052")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updateUser.toString())
                .header("ApiKeyAuth", "1111-abcd-5678-efgh"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateUserByIdWithInvalidTokenShouldReturnForbidden() throws Exception {

        JSONObject updateUser = new JSONObject();
        updateUser.put("firstName", "John");
        updateUser.put("lastName", "Doe");
        updateUser.put("password", "Password1!");
        updateUser.put("email", "johndoe@gmail.com");

        mvc.perform(put("/users/100052")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updateUser.toString())
                .header("ApiKeyAuth", "InvalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateUserByIdWithNonExistingIdShouldReturnNotAcceptable() throws Exception {

        JSONObject updateUser = new JSONObject();
        updateUser.put("firstName", "John");
        updateUser.put("lastName", "Doe");
        updateUser.put("password", "Password1!");
        updateUser.put("email", "johndoe@gmail.com");

        mvc.perform(put("/users/0000")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updateUser.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void updateUserByIdWithoutRequiredInputShouldReturnNotAcceptable() throws Exception {

        JSONObject updateUser = new JSONObject();

        mvc.perform(put("/users/100052")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updateUser.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void updateUserByIdWithInvalidPasswordFormatShouldReturnNotAcceptable() throws Exception {

        JSONObject updateUser = new JSONObject();
        updateUser.put("firstName", "John");
        updateUser.put("lastName", "Doe");
        updateUser.put("password", "password");
        updateUser.put("email", "johndoe@gmail.com");

        mvc.perform(put("/users/100052")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updateUser.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void updateUserByIdWithInvalidEmailFormatShouldReturnNotAcceptable() throws Exception {

        JSONObject updateUser = new JSONObject();
        updateUser.put("firstName", "John");
        updateUser.put("lastName", "Doe");
        updateUser.put("password", "Password1!");
        updateUser.put("email", "johndoegmailcom");

        mvc.perform(put("/users/100052")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updateUser.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }
}
