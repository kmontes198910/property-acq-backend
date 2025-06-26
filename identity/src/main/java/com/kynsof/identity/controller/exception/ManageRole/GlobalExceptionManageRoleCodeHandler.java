package com.kynsof.identity.controller.exception.ManageRole;

import com.kynsof.identity.application.response.ErrorResponse;
import com.kynsof.identity.domain.dto.exception.manageRole.ManageRoleCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionManageRoleCodeHandler {

    @ExceptionHandler(ManageRoleCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(ManageRoleCodeException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Code already exists.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
