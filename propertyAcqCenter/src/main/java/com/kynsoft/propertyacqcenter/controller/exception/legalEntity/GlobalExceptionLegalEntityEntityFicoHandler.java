package com.kynsoft.propertyacqcenter.controller.exception.legalEntity;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.legalEntity.LegalEntityEntityFicoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionLegalEntityEntityFicoHandler {

    @ExceptionHandler(LegalEntityEntityFicoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(LegalEntityEntityFicoException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "El puntaje FICO debe estar entre 300 y 850.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
