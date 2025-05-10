package com.kynsoft.propertyacqcenter.application.command.legalEntity.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.EntityStatus;
import com.kynsoft.propertyacqcenter.domain.enums.EntityType;
import com.kynsoft.propertyacqcenter.domain.enums.Month;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateLegalEntityCommand implements ICommand {

    private UUID id;
    private String name;
    private String taxId;
    private EntityType entityType;
    private UUID business;
    private String formationState;
    private LocalDate formationDate;
    private Month fiscalYearEnd;
    private String businessDescription;
    private String website;
    private String industry;
    private Double annualRevenue;
    private LocalDate dateOfLastAnnualReport;
    private UUID parentEntityId;
    private String notes;
    private EntityStatus status;
    private String owner;
    private String entityExperience;
    private Float entityFico;

    public UpdateLegalEntityCommand(UUID id, String name, String taxId, EntityType entityType, 
            UUID business, String formationState, LocalDate formationDate, 
            Month fiscalYearEnd, String businessDescription, 
            String website, String industry, Double annualRevenue, 
            LocalDate dateOfLastAnnualReport, UUID parentEntityId, String notes, EntityStatus status,
            String owner, String entityExperience, Float entityFico) {
        this.id = id;
        this.name = name;
        this.taxId = taxId;
        this.entityType = entityType;
        this.business = business;
        this.formationState = formationState;
        this.formationDate = formationDate;
        this.fiscalYearEnd = fiscalYearEnd;
        this.businessDescription = businessDescription;
        this.website = website;
        this.industry = industry;
        this.annualRevenue = annualRevenue;
        this.dateOfLastAnnualReport = dateOfLastAnnualReport;
        this.parentEntityId = parentEntityId;
        this.notes = notes;
        this.status = status;
        this.owner = owner;
        this.entityExperience = entityExperience;
        this.entityFico = entityFico;
    }

    public static UpdateLegalEntityCommand fromRequest(UpdateLegalEntityRequest request, UUID id) {
        return new UpdateLegalEntityCommand(
                id,
                request.getName(),
                request.getTaxId(),
                request.getEntityType(),
                request.getBusiness(),
                request.getFormationState(),
                request.getFormationDate(),
                request.getFiscalYearEnd(),
                request.getBusinessDescription(),
                request.getWebsite(),
                request.getIndustry(),
                request.getAnnualRevenue(),
                request.getDateOfLastAnnualReport(),
                request.getParentEntityId(),
                request.getNotes(),
                request.getStatus(),
                request.getOwner(),
                request.getEntityExperience(),
                request.getEntityFico()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateLegalEntityMessage(id);
    }
}
