package com.kynsoft.propertyacqcenter.controller.exception.property;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.property.PropertyIdMustBeUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionPropertyIdHandler {

    @ExceptionHandler(PropertyIdMustBeUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(PropertyIdMustBeUniqueException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "The property id, already exists.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
