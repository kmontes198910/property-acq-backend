package com.kynsof.hospitalizationService.infrastructure.controller.exception;

import com.kynsof.hospitalizationService.domain.dto.exception.DiagnosisNotFoundException;
import com.kynsof.hospitalizationService.domain.exception.entity.ErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionDiagnosisHandler {

    @ExceptionHandler(DiagnosisNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmergencyCaseNotFound(DiagnosisNotFoundException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Diagnosis Not Found.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
