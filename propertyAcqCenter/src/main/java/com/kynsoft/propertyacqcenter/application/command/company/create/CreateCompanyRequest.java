package com.kynsoft.propertyacqcenter.application.command.company.create;

import com.kynsoft.propertyacqcenter.domain.enums.CompanyType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateCompanyRequest {
    private UUID business;
    private UUID companyType;
    private UUID subCompanyType;
    private String title;
    private String notes;
    private String category;
    private UUID subCategory;
    private CompanyType type;
    private CreateTitleCompanyDataRequest titleCompany;
}