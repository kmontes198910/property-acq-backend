package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCompanyTypeDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCompanyTypeResponse implements IResponse {

    private UUID id;
    private String name;
    private CompanyTypeDto companyType;
    private String description;
    private String code;
    private Boolean isSpecialized;
    private String specializationArea;
    private Boolean requiresLicense;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SubCompanyTypeResponse(SubCompanyTypeDto dto) {
        this.id = dto.getId();
        this.companyType = dto.getCompanyType();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.code = dto.getCode();
        this.isSpecialized = dto.getIsSpecialized();
        this.specializationArea = dto.getSpecializationArea();
        this.requiresLicense = dto.getRequiresLicense();
        this.isActive = dto.getIsActive();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
    }

}