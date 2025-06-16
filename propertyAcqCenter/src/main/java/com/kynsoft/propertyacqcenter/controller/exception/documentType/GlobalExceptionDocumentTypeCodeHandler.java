package com.kynsoft.propertyacqcenter.controller.exception.documentType;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.documentType.DocumentTypeCodeMustBeUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionDocumentTypeCodeHandler {

    @ExceptionHandler(DocumentTypeCodeMustBeUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(DocumentTypeCodeMustBeUniqueException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Code already exists.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
