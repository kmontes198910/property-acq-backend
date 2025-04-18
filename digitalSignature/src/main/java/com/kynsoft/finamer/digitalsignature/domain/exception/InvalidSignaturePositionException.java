package com.kynsoft.finamer.digitalsignature.domain.exception;

public class InvalidSignaturePositionException extends RuntimeException {
    private final ApiError error;

    public InvalidSignaturePositionException() {
        super(ApiError.INVALID_SIGNATURE_POSITION.getMessage());
        this.error = ApiError.INVALID_SIGNATURE_POSITION;
    }
    
    public InvalidSignaturePositionException(String message) {
        super(message);
        this.error = ApiError.INVALID_SIGNATURE_POSITION;
    }

    public ApiError getError() {
        return error;
    }
}