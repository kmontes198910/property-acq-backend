package com.kynsoft.propertyacqcenter.domain.dto.exception.employee;

public class EmployeeEmailFormatException extends RuntimeException {
    public EmployeeEmailFormatException(String email) {
        super("Invalid email format: " + email);
    }
}
