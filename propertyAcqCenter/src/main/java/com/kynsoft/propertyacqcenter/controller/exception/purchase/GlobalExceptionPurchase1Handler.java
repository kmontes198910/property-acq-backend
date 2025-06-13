package com.kynsoft.propertyacqcenter.controller.exception.purchase;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.purchase.PurchaseMustBeUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionPurchase1Handler {

    @ExceptionHandler(PurchaseMustBeUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(PurchaseMustBeUniqueException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "A purchase already exists for this property.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
