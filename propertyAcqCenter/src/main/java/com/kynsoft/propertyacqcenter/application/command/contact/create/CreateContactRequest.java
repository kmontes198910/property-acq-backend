package com.kynsoft.propertyacqcenter.application.command.contact.create;

import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import com.kynsoft.propertyacqcenter.domain.enums.DepartmentType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateContactRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String position;
    private DepartmentType department;
    private ContactType category;
    private String notes;
    private Boolean isActive;
    private UUID legalEntity;
    private String personalEmail;
    private UUID subCategory;
}
