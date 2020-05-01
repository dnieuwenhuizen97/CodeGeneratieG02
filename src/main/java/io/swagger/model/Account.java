package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Account
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-28T15:21:59.457Z[GMT]")
public class Account   {
  @JsonProperty("iban")
  private String iban = null;

  /**
   * Gets or Sets accountType
   */
  public enum AccountTypeEnum {
    CURRENT("current"),
    
    SAVINGS("savings");

    private String value;

    AccountTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AccountTypeEnum fromValue(String text) {
      for (AccountTypeEnum b : AccountTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("account_type")
  @Valid
  private List<AccountTypeEnum> accountType = null;

  @JsonProperty("balance")
  private Float balance = null;

  @JsonProperty("transactionDayLimit")
  private Integer transactionDayLimit = null;

  @JsonProperty("transactionAmountLimit")
  private BigDecimal transactionAmountLimit = null;

  @JsonProperty("balanceLimit")
  private BigDecimal balanceLimit = null;

  @JsonProperty("owner")
  private Integer owner = null;

  public Account iban(String iban) {
    this.iban = iban;
    return this;
  }

  /**
   * Get iban
   * @return iban
  **/
  @ApiModelProperty(example = "NL11INHO0123456789", value = "")
  
  @Pattern(regexp="^\\w{2}\\d{2}\\w{4}\\d{10}$")   public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public Account accountType(List<AccountTypeEnum> accountType) {
    this.accountType = accountType;
    return this;
  }

  public Account addAccountTypeItem(AccountTypeEnum accountTypeItem) {
    if (this.accountType == null) {
      this.accountType = new ArrayList<AccountTypeEnum>();
    }
    this.accountType.add(accountTypeItem);
    return this;
  }

  /**
   * Get accountType
   * @return accountType
  **/
  @ApiModelProperty(value = "")
  
    public List<AccountTypeEnum> getAccountType() {
    return accountType;
  }

  public void setAccountType(List<AccountTypeEnum> accountType) {
    this.accountType = accountType;
  }

  public Account balance(Float balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
  **/
  @ApiModelProperty(example = "200", value = "")
  
    public Float getBalance() {
    return balance;
  }

  public void setBalance(Float balance) {
    this.balance = balance;
  }

  public Account transactionDayLimit(Integer transactionDayLimit) {
    this.transactionDayLimit = transactionDayLimit;
    return this;
  }

  /**
   * Get transactionDayLimit
   * @return transactionDayLimit
  **/
  @ApiModelProperty(example = "100", value = "")
  
    public Integer getTransactionDayLimit() {
    return transactionDayLimit;
  }

  public void setTransactionDayLimit(Integer transactionDayLimit) {
    this.transactionDayLimit = transactionDayLimit;
  }

  public Account transactionAmountLimit(BigDecimal transactionAmountLimit) {
    this.transactionAmountLimit = transactionAmountLimit;
    return this;
  }

  /**
   * Get transactionAmountLimit
   * @return transactionAmountLimit
  **/
  @ApiModelProperty(example = "200", value = "")
  
    @Valid
    public BigDecimal getTransactionAmountLimit() {
    return transactionAmountLimit;
  }

  public void setTransactionAmountLimit(BigDecimal transactionAmountLimit) {
    this.transactionAmountLimit = transactionAmountLimit;
  }

  public Account balanceLimit(BigDecimal balanceLimit) {
    this.balanceLimit = balanceLimit;
    return this;
  }

  /**
   * Get balanceLimit
   * @return balanceLimit
  **/
  @ApiModelProperty(example = "-1200", value = "")
  
    @Valid
    public BigDecimal getBalanceLimit() {
    return balanceLimit;
  }

  public void setBalanceLimit(BigDecimal balanceLimit) {
    this.balanceLimit = balanceLimit;
  }

  public Account owner(Integer owner) {
    this.owner = owner;
    return this;
  }

  /**
   * id of owning user
   * @return owner
  **/
  @ApiModelProperty(example = "1", value = "id of owning user")
  
    public Integer getOwner() {
    return owner;
  }

  public void setOwner(Integer owner) {
    this.owner = owner;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return Objects.equals(this.iban, account.iban) &&
        Objects.equals(this.accountType, account.accountType) &&
        Objects.equals(this.balance, account.balance) &&
        Objects.equals(this.transactionDayLimit, account.transactionDayLimit) &&
        Objects.equals(this.transactionAmountLimit, account.transactionAmountLimit) &&
        Objects.equals(this.balanceLimit, account.balanceLimit) &&
        Objects.equals(this.owner, account.owner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(iban, accountType, balance, transactionDayLimit, transactionAmountLimit, balanceLimit, owner);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Account {\n");
    
    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
    sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    transactionDayLimit: ").append(toIndentedString(transactionDayLimit)).append("\n");
    sb.append("    transactionAmountLimit: ").append(toIndentedString(transactionAmountLimit)).append("\n");
    sb.append("    balanceLimit: ").append(toIndentedString(balanceLimit)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
