package com.kynsof.hospitalizationService.infrastructure.controller.exception;

import com.kynsof.hospitalizationService.application.response.ErrorResponse;
import com.kynsof.hospitalizationService.domain.dto.exception.MedicalPrescriptionDtoNotFoundException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionMedicalPrescriptionHandler {

    @ExceptionHandler(MedicalPrescriptionDtoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmergencyCaseNotFound(MedicalPrescriptionDtoNotFoundException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Medical Prescription Not Found.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
