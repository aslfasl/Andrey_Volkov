package com.volkov.exceptions;

import lombok.Getter;

@Getter
public class InsuranceException extends RuntimeException {

    private final ErrorType errorType;

    public InsuranceException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}
