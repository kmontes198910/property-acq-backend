package com.kynsoft.propertyacqcenter.application.command.titleCompany.update;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateTitleCompanyRequest {
    private UUID business;
    private UUID companyType;
    private UUID subCompanyType;
    private String title;
    private String notes;
    private String category;
    private UUID subCategory;
    private UpdateTitleCompanyDataRequest titleCompany;
}