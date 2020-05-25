package io.swagger.api;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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


    //machine transfer
    @Test
    public void userPerformMachineTransferWithValidInputShouldReturnIsCreated() throws Exception{

        JSONObject machineTransfer = new JSONObject();
        machineTransfer.put("amount", 400);
        machineTransfer.put("transfer_type", "deposit");

        mvc.perform(post("/users/100001/machine")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(machineTransfer.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "    'transaction_id': 100004,\n" +
                        "    'transaction_type': 'deposit',\n" +
                        "    'timestamp': '"+ LocalDateTime.now().withSecond(0).withNano(0) +":00',\n" +
                        "    'account_from': 'NL01INHO0000000001',\n" +
                        "    'account_to': 'NL01INHO0000000001',\n" +
                        "    'amount': 400.0,\n" +
                        "    'user_performing': 100001\n" +
                        "}"));

    }
    @Test
    public void userPerformMachineTransferWithoutRequiredInputShouldReturnBadRequest() throws Exception{
        JSONObject machineTransfer = new JSONObject();
        machineTransfer.put("amount", 400);
        machineTransfer.put("transfer_type", null);

        mvc.perform(post("/users/100001/machine")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(machineTransfer.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void userPerformMachineTransferWithNotAcceptableAmountShouldReturnIsNotAcceptable() throws Exception{

        //machine withdraw that is more than the  account balance
        JSONObject machineTransfer = new JSONObject();
        machineTransfer.put("amount", 400000);
        machineTransfer.put("transfer_type", "withdraw");

        mvc.perform(post("/users/100001/machine")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(machineTransfer.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }
    @Test
    public void userPerformMachineTransferWithoutHavingACurrentAccountShouldReturnIsNotAcceptable() throws Exception{

        JSONObject machineTransfer = new JSONObject();
        machineTransfer.put("amount", 10);
        machineTransfer.put("transfer_type", "withdraw");

        //user 100004 has no current account
        mvc.perform(post("/users/100004/machine")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(machineTransfer.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }
    @Test
    public void userPerformMachineTransferWithoutValidTokenShouldReturnForbidden() throws Exception{

        JSONObject machineTransfer = new JSONObject();
        machineTransfer.put("amount", 400);
        machineTransfer.put("transfer_type", "deposit");

        mvc.perform(post("/users/100001/machine")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(machineTransfer.toString())
                .header("ApiKeyAuth", "no valid token"))
                .andExpect(status().isForbidden());
    }



    //user requests
    @Test
    public void usersGetRegisterRequestsWithValidAuthTokenAndTheValidRoleShouldReturnIsOk() throws Exception{
        mvc.perform(get("/users/requests")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isOk());
    }
    @Test
    public void usersGetRegisterRequestsWithInvalidAuthTokenShouldReturnIsForbidden() throws Exception{
        mvc.perform(get("/users/requests")
                .header("ApiKeyAuth", "no valid token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void usersGetRegisterRequestsWithValidAuthTokenButInvalidRoleShouldReturnIsForbidden() throws Exception{
        mvc.perform(get("/users/requests")
                .header("ApiKeyAuth", "1111-abcd-5678-efgh"))
                .andExpect(status().isForbidden());
    }
}
