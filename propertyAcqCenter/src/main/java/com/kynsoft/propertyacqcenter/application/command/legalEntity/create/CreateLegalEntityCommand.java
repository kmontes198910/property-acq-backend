package com.kynsoft.propertyacqcenter.application.command.legalEntity.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.EntityStatus;
import com.kynsoft.propertyacqcenter.domain.enums.EntityType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateLegalEntityCommand implements ICommand {

    private UUID id;
    private String name;
    private String taxId;
    private EntityType entityType;
    private UUID business;
    private String formationState;
    private LocalDate formationDate;
    private String fiscalYearEnd;
    private String businessDescription;
    private String registrationNumber;
    private String website;
    private String industry;
    private Double annualRevenue;
    private LocalDate dateOfLastAnnualReport;
    private UUID parentEntityId;
    private String notes;
    private EntityStatus status;

    public CreateLegalEntityCommand(String name, String taxId, EntityType entityType, 
            UUID business, String formationState, LocalDate formationDate, 
            String fiscalYearEnd, String businessDescription, String registrationNumber, 
            String website, String industry, Double annualRevenue,
            LocalDate dateOfLastAnnualReport, UUID parentEntityId, String notes, EntityStatus status) {
        this.id = UUID.randomUUID();
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
        this.dateOfLastAnnualReport = dateOfLastAnnualReport;
        this.parentEntityId = parentEntityId;
        this.notes = notes;
        this.status = status;
    }

    public static CreateLegalEntityCommand fromRequest(CreateLegalEntityRequest request) {
        return new CreateLegalEntityCommand(
                request.getName(),
                request.getTaxId(),
                request.getEntityType(),
                request.getBusiness(),
                request.getFormationState(),
                request.getFormationDate(),
                request.getFiscalYearEnd(),
                request.getBusinessDescription(),
                request.getRegistrationNumber(),
                request.getWebsite(),
                request.getIndustry(),
                request.getAnnualRevenue(),
                request.getDateOfLastAnnualReport(),
                request.getParentEntityId(),
                request.getNotes(),
                request.getStatus()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateLegalEntityMessage(id);
    }
}
