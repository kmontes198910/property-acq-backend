package com.kynsoft.propertyacqcenter.domain.dto.exception.employee;

public class EmployeeEmployeeNumberMustBeUniqueException extends RuntimeException {
    public EmployeeEmployeeNumberMustBeUniqueException(String employeeNumber) {
        super("The employee number: " + employeeNumber + " already exists. ");
    }
}
