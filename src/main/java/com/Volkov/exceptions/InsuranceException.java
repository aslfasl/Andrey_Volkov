package com.Volkov.exceptions;

import lombok.Getter;

@Getter
public class InsuranceException extends Exception {
    private final ErrorType errorType;

    public InsuranceException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}
