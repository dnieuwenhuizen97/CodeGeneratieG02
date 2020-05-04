package io.swagger.model;

import java.time.LocalDateTime;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AuthToken that gets returned if the specified username and password is correct.
 */
@ApiModel(description = "AuthToken that gets returned if the specified username and password is correct.")
@Validated
@Entity
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-05-03T10:32:36.707Z[GMT]")
public class AuthToken   {
  @JsonProperty("AuthToken")
  private String authToken = null;

  public AuthToken authToken(String authToken) {
    this.authToken = authToken;
    return this;
  }

 private Integer userId = null;

  @Id
  @SequenceGenerator(name="seq", initialValue = 100001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
  private Long authTokenId = null;
  private LocalDateTime tokenCreated = null;

  public AuthToken(String authToken, Integer userId, LocalDateTime tokenCreated) {
    this.authToken = authToken;
    this.userId = userId;
    this.tokenCreated = tokenCreated;
  }

  public AuthToken()
  {

  }

    public Integer getUserId() {
        return userId;
    }

    public Long getAuthTokenId() {
        return authTokenId;
    }

    public LocalDateTime getTokenCreated() {
        return tokenCreated;
    }

    /**
   * Get authToken
   * @return authToken
  **/
  @ApiModelProperty(example = "1234-abcd-5678-efgh", value = "")
  
    public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthToken authToken = (AuthToken) o;
    return Objects.equals(this.authToken, authToken.authToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authToken);
  }

  @Override
  public String toString() {
    return "AuthToken{" +
            "authToken='" + authToken + '\'' +
            ", userId=" + userId +
            ", authTokenId=" + authTokenId +
            ", tokenCreated=" + tokenCreated +
            '}';
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
