package com.Volkov.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ExHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InsuranceException.class})
    public ResponseEntity<ErrorMessage> handleInsuranceException(InsuranceException insuranceException) {
        logger.error(insuranceException.getMessage());
        ErrorType errorType =insuranceException.getErrorType();
        return new ResponseEntity<>(new ErrorMessage(-19, errorType.name()), errorType.getStatus());
    }
}
