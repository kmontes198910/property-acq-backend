package com.kynsoft.propertyacqcenter.domain.dto.embedded.company;

import com.kynsoft.propertyacqcenter.domain.enums.EntityType;
import com.kynsoft.propertyacqcenter.domain.enums.Month;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegalInformationDto {
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
