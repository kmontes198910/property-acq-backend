package com.kynsoft.propertyacqcenter.application.command.employee.update;

import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class UpdateEmployeeRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private String position;
    private String department;
    private Double salary;
    private Boolean active;
    private UUID business;
}
