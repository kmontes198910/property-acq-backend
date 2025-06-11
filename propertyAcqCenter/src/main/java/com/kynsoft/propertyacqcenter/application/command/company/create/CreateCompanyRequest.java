package com.kynsoft.propertyacqcenter.application.command.company.create;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateCompanyRequest {
    private UUID business;
    private UUID companyType;
    private UUID subCompanyType;
    private String title;
    private Double ownershipPercentage;
    private Boolean signatureAuthority;
    private String notes;
    private String category;
    private UUID subCategory;
}