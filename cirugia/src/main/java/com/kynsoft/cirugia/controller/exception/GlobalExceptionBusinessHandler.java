package com.kynsoft.cirugia.controller.exception;

import com.kynsoft.cirugia.application.response.ErrorResponse;
import com.kynsoft.cirugia.domain.dto.exception.BusinessNotFoundException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionBusinessHandler {

    @ExceptionHandler(BusinessNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmergencyCaseNotFound(BusinessNotFoundException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Business Not Found.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
