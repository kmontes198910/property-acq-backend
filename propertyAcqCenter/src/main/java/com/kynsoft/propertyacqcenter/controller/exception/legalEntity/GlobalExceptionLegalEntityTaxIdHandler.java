package com.kynsoft.propertyacqcenter.controller.exception.legalEntity;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.legalEntity.LegalEntityTaxIdMustBeUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionLegalEntityTaxIdHandler {

    @ExceptionHandler(LegalEntityTaxIdMustBeUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(LegalEntityTaxIdMustBeUniqueException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "TaxId already exists.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
