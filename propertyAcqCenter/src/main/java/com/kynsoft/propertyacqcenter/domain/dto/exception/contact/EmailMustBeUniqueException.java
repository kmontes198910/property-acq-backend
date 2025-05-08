package com.kynsoft.propertyacqcenter.domain.dto.exception.contact;

public class EmailMustBeUniqueException extends RuntimeException {
    public EmailMustBeUniqueException(String email) {
        super("The email: " + email + " already exists. ");
    }
}
