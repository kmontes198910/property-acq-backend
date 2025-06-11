package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private UUID id;
    private BusinessDto business;
    private CompanyTypeDto companyType;
    private SubCompanyTypeDto subCompanyType;
    private String title;//
    private Double ownershipPercentage;//
    private Boolean signatureAuthority;//
    private String notes;//
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;//
    private UUID updatedBy;//
    private String category;
    private SubCategoryDto subCategory;
}