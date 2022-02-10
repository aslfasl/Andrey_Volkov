package com.Volkov.exceptions;

import lombok.Getter;

@Getter
public class ObjectAlreadyExistsException extends RuntimeException{

    private final ErrorType errorType;

    public ObjectAlreadyExistsException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}
