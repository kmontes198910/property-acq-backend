package com.kynsoft.propertyacqcenter.domain.dto.exception.contact;

public class EmailAndPhoneNotNullException extends RuntimeException {
    public EmailAndPhoneNotNullException() {
        super("At least one of the contact fields (email or phone number) must be complete.");
    }
}
