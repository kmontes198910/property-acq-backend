package com.kynsof.hospitalizationService.infrastructure.controller.exception;

import com.kynsof.hospitalizationService.domain.dto.exception.TreatmentPlanNotFoundException;
import com.kynsof.hospitalizationService.application.response.ErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionTreatmentPlanHandler {

    @ExceptionHandler(TreatmentPlanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmergencyCaseNotFound(TreatmentPlanNotFoundException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Treatment Plan Not Found.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
