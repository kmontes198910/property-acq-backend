package com.kynsoft.propertyacqcenter.domain.dto.exception.companyContact;

public class PersonEmailFormatException extends RuntimeException {
    public PersonEmailFormatException(String email) {
        super("Invalid person email format: " + email);
    }
}
