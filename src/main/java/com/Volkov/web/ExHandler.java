package com.Volkov.web;

import com.Volkov.exceptions.ErrorMessage;
import com.Volkov.exceptions.ErrorType;
import com.Volkov.exceptions.InsuranceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InsuranceException.class})
    public ResponseEntity<ErrorMessage> handleInsuranceException(InsuranceException insuranceException) {
        log.error(insuranceException.getMessage());
        ErrorType errorType = insuranceException.getErrorType();
        return new ResponseEntity<>(new ErrorMessage(-9, errorType.name(), insuranceException.getMessage()),
                errorType.getStatus());
    }
}
