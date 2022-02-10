package com.Volkov.exceptions;

import lombok.Getter;

@Getter
public class WrongAgeException extends RuntimeException{

    private final ErrorType errorType;

    public WrongAgeException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}
