package com.kynsoft.propertyacqcenter.domain.dto.exception.contact;

public class EmailFormatException extends RuntimeException {
    public EmailFormatException(String email) {
        super("Invalid email format: " + email);
    }
}
