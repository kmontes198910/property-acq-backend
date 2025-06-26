package com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.company;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.company.LegalInformationDto;
import com.kynsoft.propertyacqcenter.domain.enums.EntityType;
import com.kynsoft.propertyacqcenter.domain.enums.Month;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "company_legal_information")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegalInformation {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company; // Relación bidireccional

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "tax_id", nullable = true)
    private String taxId;

    @Column(name = "entity_experience", nullable = true)
    private String entityExperience;

    @Column(name = "entity_fico", nullable = true)
    private Float entityFico;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = true)
    private EntityType entityType;

    @Column(name = "formation_state", nullable = true)
    private String formationState;

    @Column(name = "formation_date", nullable = true)
    private LocalDate formationDate;

    @Column(name = "fiscal_year_end", nullable = true)
    private Month fiscalYearEnd;

    @Column(name = "business_description", columnDefinition = "TEXT", nullable = true)
    private String businessDescription;

    @Column(name = "website", nullable = true)
    private String website;

    @Column(name = "annual_revenue", nullable = true)
    private Double annualRevenue;

    @Column(name = "authorized_signer_government_id_copy", nullable = true)
    private String authorizedSignerGovernmentIdCopy;

    @Column(name = "authorized_signer_government_id_copy_file_name", nullable = true)
    private String authorizedSignerGovernmentIdCopyFileName;

    @Column(name = "date_of_last_annual_report", nullable = true)
    private LocalDate dateOfLastAnnualReport;

    @Column(name = "owner", nullable = true)
    private String owner;

    public LegalInformation(LegalInformationDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.taxId = dto.getTaxId();
        this.entityExperience = dto.getEntityExperience();
        this.entityFico = dto.getEntityFico();
        this.entityType = dto.getEntityType();
        this.formationState = dto.getFormationState();
        this.formationDate = dto.getFormationDate();
        this.fiscalYearEnd = dto.getFiscalYearEnd();
        this.businessDescription = dto.getBusinessDescription();
        this.website = dto.getWebsite();
        this.annualRevenue = dto.getAnnualRevenue();
        this.authorizedSignerGovernmentIdCopy = dto.getAuthorizedSignerGovernmentIdCopy();
        this.authorizedSignerGovernmentIdCopyFileName = dto.getAuthorizedSignerGovernmentIdCopyFileName();
        this.dateOfLastAnnualReport = dto.getDateOfLastAnnualReport();
        this.owner = dto.getOwner();
    }

}
