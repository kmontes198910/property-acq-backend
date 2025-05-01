package com.kynsoft.propertyacqcenter.application.command.company.create;

import com.kynsoft.propertyacqcenter.domain.enums.ContactRole;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class CreateCompanyRequest {
    private UUID legalEntityId;
    private String firstName;
    private String lastName;
    private ContactRole role;
    private String email;
    private String phone;
    private String cellPhone;
    private String title;
    private LocalDate dateOfBirth;
    private String personalTaxId;
    private String nationality;
    private String personalAddress;
    private String city;
    private String state;
    private String zipCode;
    private String personalEmail;
    private Boolean isPrimary;
    private Double ownershipPercentage;
    private Boolean signatureAuthority;
    private String notes;
    private UUID createdBy;
}