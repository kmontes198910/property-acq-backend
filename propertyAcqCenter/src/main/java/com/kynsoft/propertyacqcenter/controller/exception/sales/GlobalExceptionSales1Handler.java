package com.kynsoft.propertyacqcenter.controller.exception.sales;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.sales.SalesMustBeUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionSales1Handler {

    @ExceptionHandler(SalesMustBeUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(SalesMustBeUniqueException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "A sales already exists for this property.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
