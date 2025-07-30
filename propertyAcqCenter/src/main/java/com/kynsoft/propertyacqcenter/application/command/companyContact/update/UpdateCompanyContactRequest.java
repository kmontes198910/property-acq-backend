package com.kynsoft.propertyacqcenter.application.command.companyContact.update;

import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import com.kynsoft.propertyacqcenter.domain.enums.DepartmentType;
import com.kynsoft.propertyacqcenter.domain.enums.Type;
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
    private String notes;
    private Boolean isActive;
    private String personalEmail;
    private LocalDate birthDate;
    private Boolean isEmployee;
    private String mailingAddress;

    private UUID legalEntity;
    private UUID subCategory;
    private ContactType category;
    private Type type;
}
