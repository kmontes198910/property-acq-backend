package com.kynsoft.propertyacqcenter.controller.exception.contact;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.contact.EmailFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionContactEmailFormatHandler {

    @ExceptionHandler(EmailFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(EmailFormatException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Invalid email format.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
