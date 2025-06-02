package com.kynsoft.propertyacqcenter.domain.dto.exception.companyContact;

public class PersonEmailMustBeUniqueException extends RuntimeException {
    public PersonEmailMustBeUniqueException(String email) {
        super("The person email: " + email + " already exists. ");
    }
}
