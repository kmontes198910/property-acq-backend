package com.kynsoft.propertyacqcenter.controller.exception.contact;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.contact.EmailAndPhoneNotNullException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionEmailAndPhoneNotNullHandler {

    @ExceptionHandler(EmailAndPhoneNotNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(EmailAndPhoneNotNullException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "At least one of the contact fields (email or phone number) must be complete.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
