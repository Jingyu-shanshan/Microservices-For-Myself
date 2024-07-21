package com.microservices.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class LikeApiException extends RuntimeException {

    @Getter
    private HttpStatus status;
    private String message;

    public LikeApiException(String message, HttpStatus status, String errorMessage) {
        super(message);
        this.status = status;
        this.message = errorMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
