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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = webAppContextSetup(webApplicationContext).build();
    }


    //create transaction
    @Test
    public void userPerformTransactionWithValidInputShouldReturnCreated() throws Exception{

        JSONObject transaction = new JSONObject();
        transaction.put("account_from", "NL88INHO0993873040");
        transaction.put("account_to", "NL06INHO0463973767");
        transaction.put("amount", 25);

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(transaction.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "    'transaction_id': 100016,\n" +
                        "    'transaction_type': 'transaction',\n" +
                        "    'account_from': 'NL88INHO0993873040',\n" +
                        "    'account_to': 'NL06INHO0463973767',\n" +
                        "    'amount': 25.0,\n" +
                        "    'user_performing': 100052\n" +
                        "}"));

    }


    @Test
    public void userPerformTransactionWithoutValidTokenShouldReturnForbidden() throws Exception{
        JSONObject transaction = new JSONObject();
        transaction.put("account_from", "NL04INHO0463973767");
        transaction.put("account_to", "NL04INHO0463973767");
        transaction.put("amount", 5);

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(transaction.toString())
                .header("ApiKeyAuth", "no valid token"))
                .andExpect(status().isForbidden());

    }


    @Test
    public void userPerformTransactionWithoutRequiredInputShouldReturnUnprocessableEntity() throws Exception{
        JSONObject transaction = new JSONObject();
        transaction.put("account_from", "NL04INHO0463973767");
        transaction.put("account_to", null);
        transaction.put("amount", 5);

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(transaction.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void userPerformTransactionWithNotAcceptableAmountShouldReturnUnprocessableEntity() throws Exception{
        JSONObject transaction = new JSONObject();
        transaction.put("account_from", "NL04INHO0463973767");
        transaction.put("account_to", "NL06INHO0463973767");
        transaction.put("amount", 50000);

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(transaction.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json("{\n" +
                        "   'message': 'You do not have enough balance to transfer this amount!' \n"+
                        "}"));

    }

    @Test
    public void userPerformTransactionWithNegativeAmountShouldReturnUnprocessableEntity() throws Exception{
        JSONObject transaction = new JSONObject();
        transaction.put("account_from", "NL04INHO0463973767");
        transaction.put("account_to", "NL06INHO0463973767");
        transaction.put("amount", -10);

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(transaction.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json("{\n" +
                        "   'message': 'You cannot transfer a negative number.' \n"+
                        "}"));

    }

    @Test
    public void userPerformTransactionWithZeroAmountShouldReturnUnprocessableEntity() throws Exception{
        JSONObject transaction = new JSONObject();
        transaction.put("account_from", "NL04INHO0463973767");
        transaction.put("account_to", "NL06INHO0463973767");
        transaction.put("amount", 0);

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(transaction.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json("{\n" +
                        "   'message': 'You cannot transfer nothing.' \n"+
                        "}"));

    }

    @Test
    public void userPerformTransactionWithBothSameAccountShouldReturnUnprocessableEntity() throws Exception{
        JSONObject transaction = new JSONObject();
        transaction.put("account_from", "NL04INHO0463973767");
        transaction.put("account_to", "NL04INHO0463973767");
        transaction.put("amount", 5);

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(transaction.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json("{\n" +
                        "   'message': 'You cannot transfer to your own account!' \n"+
                        "}"));

    }

    @Test
    public void userPerformTransactionToOtherSavingAccountShouldReturnUnprocessableEntity() throws Exception{
        JSONObject transaction = new JSONObject();
        transaction.put("account_from", "NL67INHO0463973767");
        transaction.put("account_to", "NL88INHO0993873040");
        transaction.put("amount", 5);

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(transaction.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json("{\n" +
                        "   'message': 'You cannot transfer the funds to a savings account.' \n"+
                        "}"));

    }

    //External Bank account
    @Test
    public void userPerformTransactionToExternalBankAccountShouldReturnCreated() throws Exception{
        JSONObject transaction = new JSONObject();
        transaction.put("account_from", "NL04INHO0463973767");
        transaction.put("account_to", "ND06INHO0463973767");
        transaction.put("amount", 10);

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(transaction.toString())
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isCreated());


    }


    //getAllTransactions

    @Test
    public void employeeGetAllTransactionsWithValidAuthTokenAndTheValidRoleShouldReturnIsOk() throws Exception{
        mvc.perform(get("/transactions")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isOk());
    }

    @Test
    public void employeeGetAllTransactionsWithInValidAuthTokenAndTheValidRoleShouldReturnForbidded() throws Exception{
        mvc.perform(get("/transactions")
                .header("ApiKeyAuth", "invalid token"))
                .andExpect(status().isForbidden());
    }

    //getAllTransactions with offset
    @Test
    public void employeeGetAllTransactionsWithOffsetShouldReturnOk() throws Exception{
        mvc.perform(get("/transactions?offset=1")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isOk());
    }

    @Test
    public void employeeGetAllTransactionsWithLimitShouldReturnOk() throws Exception{
        mvc.perform(get("/transactions?limit=4")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isOk());
    }

    @Test
    public void employeeGetAllTransactionsWithInvalidLimitShouldReturnUnprocessableEntity() throws Exception{
        mvc.perform(get("/transactions?limit=-4")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isUnprocessableEntity());
    }

    //getAllTransactions with offset and limit
    @Test
    public void employeeGetAllTransactionsWithOffsetAndLimitShouldReturnOk() throws Exception{
        mvc.perform(get("/transactions?offset=1&limit=4")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isOk());
    }

    @Test
    public void employeeGetAllTransactionsWithValidIbnShouldReturnOk() throws Exception{
        mvc.perform(get("/transactions?iban=NL88INHO0993873040")
                .header("ApiKeyAuth", "1234-abcd-5678-efgh"))
                .andExpect(status().isOk());
    }

    @Test
    public void usersGetAllTransactionsWithValidAuthTokenButInvalidRoleSouldReturnForbidden() throws Exception{
        mvc.perform(get("/transactions")
                .header("ApiKeyAuth", "1000-abcd-5678-efgh"))
                .andExpect(status().isForbidden());
    }

}
