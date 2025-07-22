package com.kynsoft.propertyacqcenter.controller.exception.document;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.document.DocumentTypeCertificateOfGoodStandingMustBeUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionDocumentTypeCertificateOfGoodStandingMustBeUniqueHandler {

    @ExceptionHandler(DocumentTypeCertificateOfGoodStandingMustBeUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(DocumentTypeCertificateOfGoodStandingMustBeUniqueException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "A Certificate of Good Standing already exists for this legal entity.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
