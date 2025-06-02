package com.kynsoft.propertyacqcenter.controller.exception.bankAccount;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.bankAccount.BankAccountLegalEntityAccountNumberException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionBankAccountLegalEntityAccountNumberHandler {

    @ExceptionHandler(BankAccountLegalEntityAccountNumberException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(BankAccountLegalEntityAccountNumberException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Account number already exists for the specified legal entity.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
