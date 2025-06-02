package com.kynsoft.medicaltest.domain.exception;

public class BusinessMedicalTestException extends RuntimeException {
    private final String code;

    public BusinessMedicalTestException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

