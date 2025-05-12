package com.kynsoft.propertyacqcenter.controller.exception.contact;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.contact.LegalEntityNotNullException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionLegalEntityNotNullHandler {

    @ExceptionHandler(LegalEntityNotNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(LegalEntityNotNullException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Each contact must be assigned to a valid Legal Entity.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
