package com.microservices.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class AccountApiException extends RuntimeException {

    @Getter
    private HttpStatus status;
    private String message;

    public AccountApiException(String message, HttpStatus status, String errorMessage) {
        super(message);
        this.status = status;
        this.message = errorMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
