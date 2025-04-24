package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.EntityStatus;
import com.kynsoft.propertyacqcenter.domain.enums.EntityType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegalEntityDto {
    private UUID id;
    private String name;
    private String taxId;
    private EntityType entityType;
    private BusinessDto business;
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
    private List<AddressDto> addresses;
    private List<ContactPersonDto> contactPersons;
    private List<BankAccountDto> bankAccounts;
    private List<DocumentDto> documents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public LegalEntityDto(UUID id, String name, String taxId, EntityType entityType, BusinessDto business, String formationState, LocalDate formationDate, String fiscalYearEnd, String businessDescription, String registrationNumber, String website, String industry, Double annualRevenue, Integer employeeCount, LocalDate dateOfLastAnnualReport, UUID parentEntityId, String notes, EntityStatus status, UUID createdBy, UUID updatedBy) {
        this.id = id;
        this.name = name;
        this.taxId = taxId;
        this.entityType = entityType;
        this.business = business;
        this.formationState = formationState;
        this.formationDate = formationDate;
        this.fiscalYearEnd = fiscalYearEnd;
        this.businessDescription = businessDescription;
        this.registrationNumber = registrationNumber;
        this.website = website;
        this.industry = industry;
        this.annualRevenue = annualRevenue;
        this.employeeCount = employeeCount;
        this.dateOfLastAnnualReport = dateOfLastAnnualReport;
        this.parentEntityId = parentEntityId;
        this.notes = notes;
        this.status = status;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

}