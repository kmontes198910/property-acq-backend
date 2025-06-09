package com.kynsoft.propertyacqcenter.controller.exception.companyAddress;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.companyAddress.CompanyAddressLegalEntityIsPrimaryException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionCompanyAddressIsPrimaryHandler {

    @ExceptionHandler(CompanyAddressLegalEntityIsPrimaryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(CompanyAddressLegalEntityIsPrimaryException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "A primary address is already designated for this company.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
