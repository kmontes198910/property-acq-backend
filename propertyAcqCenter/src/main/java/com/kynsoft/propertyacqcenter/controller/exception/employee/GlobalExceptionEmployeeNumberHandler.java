package com.kynsoft.propertyacqcenter.controller.exception.employee;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.employee.EmployeeEmployeeNumberMustBeUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionEmployeeNumberHandler {

    @ExceptionHandler(EmployeeEmployeeNumberMustBeUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(EmployeeEmployeeNumberMustBeUniqueException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Employee Number already exists.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
