package com.kynsoft.propertyacqcenter.application.command.company.update;

import com.kynsoft.propertyacqcenter.domain.enums.EntityType;
import com.kynsoft.propertyacqcenter.domain.enums.Month;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateLegalInformationDataRequest {
    private UUID id;
    private String name;
    private String taxId;
    private String entityExperience;
    private Float entityFico;
    private EntityType entityType;
    private String formationState;
    private LocalDate formationDate;
    private Month fiscalYearEnd;
    private String businessDescription;
    private String website;
    private Double annualRevenue;
    private String authorizedSignerGovernmentIdCopy;
    private String authorizedSignerGovernmentIdCopyFileName;
    private LocalDate dateOfLastAnnualReport;
    private String owner;
}
