package io.swagger.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private HttpStatus httpStatus;
    private String response;

    public CustomException(HttpStatus httpStatus, String response) {
        this.httpStatus = httpStatus;
        this.response = response;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
