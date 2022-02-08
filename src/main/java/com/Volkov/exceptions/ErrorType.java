package com.Volkov.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    NOT_FOUND(HttpStatus.BAD_REQUEST),
    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR),
    WRONG_AGE(HttpStatus.BAD_REQUEST),
    NO_INSURANCE(HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS(HttpStatus.CONFLICT);

    private final HttpStatus status;

    ErrorType(HttpStatus status) {
        this.status = status;
    }
}
