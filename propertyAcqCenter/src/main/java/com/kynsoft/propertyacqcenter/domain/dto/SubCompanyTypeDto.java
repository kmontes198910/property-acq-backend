package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCompanyTypeDto {
    
    private UUID id;
    private CompanyTypeDto companyType;
    private String name;
    private String description;
    private String code;
    private Boolean isSpecialized;
    private String specializationArea;
    private Boolean requiresLicense;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SubCompanyTypeDto(UUID id, CompanyTypeDto companyType, String name, String description, String code, Boolean isSpecialized, String specializationArea, Boolean requiresLicense, Boolean isActive) {
        this.id = id;
        this.companyType = companyType;
        this.name = name;
        this.description = description;
        this.code = code;
        this.isSpecialized = isSpecialized;
        this.specializationArea = specializationArea;
        this.requiresLicense = requiresLicense;
        this.isActive = isActive;
    }

}