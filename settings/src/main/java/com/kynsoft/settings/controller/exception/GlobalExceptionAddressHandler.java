package com.kynsoft.settings.controller.exception;

import com.kynsoft.settings.application.response.ErrorResponse;
import com.kynsoft.settings.domain.dto.exception.AddressNotFoundException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAddressHandler {

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmergencyCaseNotFound(AddressNotFoundException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Address Not Found.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
