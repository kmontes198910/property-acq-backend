package com.kynsof.identity.controller.exception.ManageRole;

import com.kynsof.identity.application.response.ErrorResponse;
import com.kynsof.identity.domain.dto.exception.manageRole.ManageRoleCodeIsNullException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionManageRoleCodeIsEmptyHandler {

    @ExceptionHandler(ManageRoleCodeIsNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmergencyCaseNotFound(ManageRoleCodeIsNullException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Code is empty.",
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
    }
}
