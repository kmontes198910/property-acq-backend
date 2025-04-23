package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.EntityStatus;
import com.kynsoft.propertyacqcenter.domain.enums.EntityType;
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
    private LocalDateTime formationDate;
    private String fiscalYearEnd;
    private String businessDescription;
    private String registrationNumber;
    private String website;
    private String industry;
    private Double annualRevenue;
    private Integer employeeCount;
    private LocalDateTime dateOfLastAnnualReport;
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
}