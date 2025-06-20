package com.kynsoft.propertyacqcenter.controller.exception;

import com.kynsoft.propertyacqcenter.application.response.ErrorResponse;
import com.kynsoft.propertyacqcenter.domain.dto.exception.AcquisitionDocumentNotFoundException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAcquisitionDocumentHandler {

    @ExceptionHandler(AcquisitionDocumentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmergencyCaseNotFound(AcquisitionDocumentNotFoundException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Acquisition Document Not Found.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
