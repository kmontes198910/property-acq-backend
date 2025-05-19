package com.kynsoft.propertyacqcenter.controller.exception.propertyImagen;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.propertyImagen.PropertyImagenMainMustBeUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionPropertyImagenMainHandler {

    @ExceptionHandler(PropertyImagenMainMustBeUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(PropertyImagenMainMustBeUniqueException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "The imagen main already exists.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
