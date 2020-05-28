package io.swagger.controller;



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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(webApplicationContext).build();
    }




    @Test
    public void DeleteAccountShouldReturnIsNoContent() throws Exception{
        mvc.perform(delete("/accounts/NL05INHO0993873040")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void DeleteAccountWithNotExistingIbanShouldReturnIsNotAcceptable() throws Exception{
        mvc.perform(delete("/accounts/NL05INHO0993873040")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void DeleteAccountWithoutValidTokenShouldReturnIsForbidden() throws Exception{
        mvc.perform(delete("/accounts/NL12INHO0123456789")
                .header("ApiKeyAuth", "no valid token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void DeleteAccountWithoutAuthenticationShouldReturnIsForbidden() throws Exception {
        mvc.perform(delete("/accounts/NL12INHO0123456789")
                .header("ApiKeyAuth", "1235-abcd-5678-efgh"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void GetAllAccountsShouldReturnIsOk() throws Exception {
        mvc.perform(get("/accounts")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isOk());
    }

    @Test
    public void GetAllAccountsWithoutAuthenticationShouldReturnIsForbidden() throws Exception {
        mvc.perform(get("/accounts")
                .header("ApiKeyAuth", "1235-abcd-5678-efgh"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void GetAllAccountsWithoutValidTokenShouldReturnIsForbidden() throws Exception {
        mvc.perform(get("/accounts")
                .header("ApiKeyAuth", "no valid token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void GetAccountByIbanShouldReturnIsOk() throws Exception {
        mvc.perform(get("/accounts/NL05INHO0993873040")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    'iban': 'NL05INHO0993873040',\n" +
                        "    'account_type': 'savings',\n" +
                        "    'balance': 25.0, \n" +
                        "    'transactionDayLimit': 200.0,\n" +
                        "    'transactionAmountLimit': 3500.0,\n" +
                        "    'balanceLimit': 0.0,\n" +
                        "    'owner': 100053\n" +
                        "}"));
    }

    @Test
    public void GetAccountByIbanWithNotExistingIbanShouldReturnDeleted() throws Exception{
        mvc.perform(get("/accounts/NL11INHO0111111111")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void GetAllAccountByIbanWithoutAuthenticationShouldReturnIsForbidden() throws Exception {
        mvc.perform(get("/accounts/NL05INHO0993873040")
                .header("ApiKeyAuth", "1235-abcd-5678-efgh"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void GetAllAccountByIbanWithoutValidTokenShouldReturnIsForbidden() throws Exception {
        mvc.perform(get("/accounts/NL05INHO0993873040")
                .header("ApiKeyAuth", "no valid token"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void UpdateAccountWithValidInputShouldReturnJsonObjectAndStatusIsOk() throws Exception{
        JSONObject updateAccount = new JSONObject();
        updateAccount.put("transactionDayLimit", 150000);
        updateAccount.put("transactionAmountLimit", 6000);
        updateAccount.put("balanceLimit", -50);

        mvc.perform(put("/accounts/NL12INHO0123456789")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updateAccount.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    'iban': 'NL12INHO0123456789',\n" +
                        "    'account_type': 'current',\n" +
                        "    'balance': 0.0, \n" +
                        "    'transactionDayLimit': 150000,\n" +
                        "    'transactionAmountLimit': 6000.0,\n" +
                        "    'balanceLimit': -50.0,\n" +
                        "    'owner': 100052\n" +
                        "}"));
    }

    @Test
    public void UpdateAccountWithInvalidInputShouldReturnStatusIsBadRequest() throws Exception{
        JSONObject updateAccount = new JSONObject();
        updateAccount.put("transactionDayLimit", 150000);
        updateAccount.put("transactionAmountLimit", 6000);
        updateAccount.put("balanceLimit", "no valid balancelimit");

        mvc.perform(put("/accounts/NL12INHO0123456789")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updateAccount.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void UpdateAccountWithoutAuthenticationShouldReturnStatusIsForbidden() throws Exception{
        JSONObject updateAccount = new JSONObject();
        updateAccount.put("transactionDayLimit", 1500);
        updateAccount.put("transactionAmountLimit", 60);
        updateAccount.put("balanceLimit", 0);

        mvc.perform(put("/accounts/NL12INHO0123456789")
                .header("ApiKeyAuth", "2345-abcd-5678-efgh")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updateAccount.toString()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void UpdateAccountWithoutValidTokenShouldReturnStatusIsForbidden() throws Exception{
        JSONObject updateAccount = new JSONObject();
        updateAccount.put("transactionDayLimit", 1500);
        updateAccount.put("transactionAmountLimit", 60);
        updateAccount.put("balanceLimit", 0);

        mvc.perform(put("/accounts/NL05INHO0993873040")
                .header("ApiKeyAuth", "no valid token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(updateAccount.toString()))
                .andExpect(status().isForbidden());
    }
}

