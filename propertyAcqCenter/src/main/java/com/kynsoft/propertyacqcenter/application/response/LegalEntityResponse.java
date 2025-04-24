package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import com.kynsoft.propertyacqcenter.domain.enums.EntityStatus;
import com.kynsoft.propertyacqcenter.domain.enums.EntityType;
import java.time.LocalDate;
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
public class LegalEntityResponse implements IResponse {
    private UUID id;
    private String name;
    private String taxId;
    private EntityType entityType;
    private String formationState;
    private LocalDate formationDate;
    private String fiscalYearEnd;
    private String businessDescription;
    private String registrationNumber;
    private String website;
    private String industry;
    private Double annualRevenue;
    private Integer employeeCount;
    private LocalDate dateOfLastAnnualReport;
    private UUID parentEntityId;
    private String notes;
    private EntityStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public LegalEntityResponse(LegalEntityDto legalEntityDto) {
        this.id = legalEntityDto.getId();
        this.name = legalEntityDto.getName();
        this.taxId = legalEntityDto.getTaxId();
        this.entityType = legalEntityDto.getEntityType();
        this.formationState = legalEntityDto.getFormationState();
        this.formationDate = legalEntityDto.getFormationDate();
        this.fiscalYearEnd = legalEntityDto.getFiscalYearEnd();
        this.businessDescription = legalEntityDto.getBusinessDescription();
        this.registrationNumber = legalEntityDto.getRegistrationNumber();
        this.website = legalEntityDto.getWebsite();
        this.industry = legalEntityDto.getIndustry();
        this.annualRevenue = legalEntityDto.getAnnualRevenue();
        this.employeeCount = legalEntityDto.getEmployeeCount();
        this.dateOfLastAnnualReport = legalEntityDto.getDateOfLastAnnualReport();
        this.parentEntityId = legalEntityDto.getParentEntityId();
        this.notes = legalEntityDto.getNotes();
        this.status = legalEntityDto.getStatus();
        this.createdAt = legalEntityDto.getCreatedAt();
        this.updatedAt = legalEntityDto.getUpdatedAt();
    }

}