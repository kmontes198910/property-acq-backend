package com.kynsoft.propertyacqcenter.application.command.contact.update;

import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateContactRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String position;
    private String department;
    private ContactType category;
    private String notes;
    private Boolean isActive;
    private UUID legalEntity;
    private String personalEmail;
    private UUID subCategory;
}
