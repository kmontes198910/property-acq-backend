package com.kynsoft.propertyacqcenter.controller.exception.bankAccount;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.bankAccount.BankAccountLegalEntityRoutingNumberException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionBankAccountLegalEntityRoutingNumberHandler {

    @ExceptionHandler(BankAccountLegalEntityRoutingNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(BankAccountLegalEntityRoutingNumberException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Invalid input: must contain exactly 9 digits.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
