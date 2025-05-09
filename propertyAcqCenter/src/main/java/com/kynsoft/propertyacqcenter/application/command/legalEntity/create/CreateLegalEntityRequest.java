package com.kynsoft.propertyacqcenter.application.command.legalEntity.create;

import com.kynsoft.propertyacqcenter.domain.enums.EntityStatus;
import com.kynsoft.propertyacqcenter.domain.enums.EntityType;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLegalEntityRequest {

    private String name;
    private String taxId;
    private EntityType entityType;
    private UUID business;
    private String formationState;
    private LocalDate formationDate;
    private String fiscalYearEnd;
    private String businessDescription;
    private String website;
    private String industry;
    private Double annualRevenue;
    private LocalDate dateOfLastAnnualReport;
    private UUID parentEntityId;
    private String notes;
    private EntityStatus status;
}
