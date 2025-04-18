package com.kynsoft.finamer.digitalsignature.domain.exception;

import lombok.Getter;

@Getter
public class ApiError {
    private final String code;
    private final String message;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static final ApiError INVALID_SIGNATURE_POSITION = 
        new ApiError("SIGN-002", "Posición de firma inválida");
}