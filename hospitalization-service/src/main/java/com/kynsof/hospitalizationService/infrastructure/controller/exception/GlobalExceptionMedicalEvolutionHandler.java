package com.kynsof.hospitalizationService.infrastructure.controller.exception;

import com.kynsof.hospitalizationService.application.response.ErrorResponse;
import com.kynsof.hospitalizationService.domain.dto.exception.MedicalEvolutionNotFoundException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionMedicalEvolutionHandler {

    @ExceptionHandler(MedicalEvolutionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmergencyCaseNotFound(MedicalEvolutionNotFoundException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Medical Evolution Not Found.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
