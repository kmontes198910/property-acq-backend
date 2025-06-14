package com.kynsoft.propertyacqcenter.controller.exception;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionNotDeleteHandler {

    @ExceptionHandler(NotDeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(NotDeleteException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Element cannot be deleted has a related element.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
