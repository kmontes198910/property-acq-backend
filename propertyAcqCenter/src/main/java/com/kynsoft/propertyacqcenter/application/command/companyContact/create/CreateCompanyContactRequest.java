package com.kynsoft.propertyacqcenter.application.command.companyContact.create;

import com.kynsoft.propertyacqcenter.domain.enums.DepartmentType;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyContactRequest {

    private UUID company;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String position;
    private DepartmentType department;
    private String notes;
    private Boolean isActive;
    private String personalEmail;
    private LocalDate birthDate;
}
