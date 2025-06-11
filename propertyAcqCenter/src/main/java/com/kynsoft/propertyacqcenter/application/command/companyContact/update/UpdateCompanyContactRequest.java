package com.kynsoft.propertyacqcenter.application.command.companyContact.update;

import com.kynsoft.propertyacqcenter.domain.enums.DepartmentType;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCompanyContactRequest {

    private UUID company;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String position;
    private DepartmentType department;
    private String category;
    private String notes;
    private Boolean isActive;
    private String personalEmail;
    private UUID subCategory;
    private LocalDate birthDate;
}
