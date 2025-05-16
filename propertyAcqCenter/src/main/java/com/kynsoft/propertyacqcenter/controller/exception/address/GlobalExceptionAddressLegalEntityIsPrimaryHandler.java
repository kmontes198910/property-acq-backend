package com.kynsoft.propertyacqcenter.controller.exception.address;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.address.AddressLegalEntityIsPrimaryException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionAddressLegalEntityIsPrimaryHandler {

    @ExceptionHandler(AddressLegalEntityIsPrimaryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(AddressLegalEntityIsPrimaryException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "A primary address is already designated for this legal entity.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
