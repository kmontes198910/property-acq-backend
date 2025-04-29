package com.kynsoft.cirugia.controller.exception;

import com.kynsoft.cirugia.application.response.ErrorResponse;
import com.kynsoft.cirugia.domain.dto.exception.PatientNotFoundException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionPatientsHandler {

    @ExceptionHandler(PatientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmergencyCaseNotFound(PatientNotFoundException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Patient Not Found.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
