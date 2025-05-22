package com.kynsoft.propertyacqcenter.application.command.companyContact.update;

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
    private String department;
    private String category;
    private String notes;
    private Boolean isActive;
    private String personalEmail;
    private UUID subCategory;
}
