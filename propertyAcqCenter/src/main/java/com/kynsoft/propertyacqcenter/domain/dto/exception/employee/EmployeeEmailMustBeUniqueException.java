package com.kynsoft.propertyacqcenter.domain.dto.exception.employee;

public class EmployeeEmailMustBeUniqueException extends RuntimeException {
    public EmployeeEmailMustBeUniqueException(String email) {
        super("The email: " + email + " already exists. ");
    }
}
