package com.kynsoft.propertyacqcenter.domain.dto.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String id, String property) {
        super("Employee with " + property +": " + id + " not found");
    }
}
