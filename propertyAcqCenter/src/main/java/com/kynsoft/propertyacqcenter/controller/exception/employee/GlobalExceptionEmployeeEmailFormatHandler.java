package com.kynsoft.propertyacqcenter.controller.exception.employee;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.employee.EmployeeEmailFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionEmployeeEmailFormatHandler {

    @ExceptionHandler(EmployeeEmailFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(EmployeeEmailFormatException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Invalid email format.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
