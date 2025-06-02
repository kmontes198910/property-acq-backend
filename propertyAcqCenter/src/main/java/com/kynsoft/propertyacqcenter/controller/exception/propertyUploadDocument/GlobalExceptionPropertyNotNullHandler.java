package com.kynsoft.propertyacqcenter.controller.exception.propertyUploadDocument;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.propertyUploadDocument.PropertyNotNullException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionPropertyNotNullHandler {

    @ExceptionHandler(PropertyNotNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(PropertyNotNullException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "The document must be assigned to a property.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
