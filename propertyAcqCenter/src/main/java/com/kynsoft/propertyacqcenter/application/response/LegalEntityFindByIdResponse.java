package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import com.kynsoft.propertyacqcenter.domain.enums.EntityStatus;
import com.kynsoft.propertyacqcenter.domain.enums.EntityType;
import com.kynsoft.propertyacqcenter.domain.enums.Month;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegalEntityFindByIdResponse implements IResponse {
    private UUID id;
    private String name;
    private String taxId;
    private EntityType entityType;
    private BusinessDto business;
    private String formationState;
    private LocalDate formationDate;
    private Month fiscalYearEnd;
    private String businessDescription;
    private String website;
    private Double annualRevenue;
    private LocalDate dateOfLastAnnualReport;
    private LegalEntityBasicResponse parentEntityId;
    private String notes;
    private EntityStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private String owner;
    private String entityExperience;
    private Float entityFico;
    private String authorizedSignerGovernmentIdCopy;
    private String authorizedSignerGovernmentIdCopyFileName;
    private List<OwnerShipLegalEntityBasicResponse> owners = new ArrayList<>();

    public LegalEntityFindByIdResponse(LegalEntityDto legalEntityDto) {
        this.id = legalEntityDto.getId();
        this.name = legalEntityDto.getName();
        this.taxId = legalEntityDto.getTaxId();
        this.entityType = legalEntityDto.getEntityType();
        this.business = legalEntityDto.getBusiness();
        this.formationState = legalEntityDto.getFormationState();
        this.formationDate = legalEntityDto.getFormationDate();
        this.fiscalYearEnd = legalEntityDto.getFiscalYearEnd();
        this.businessDescription = legalEntityDto.getBusinessDescription();
        this.website = legalEntityDto.getWebsite();
        this.annualRevenue = legalEntityDto.getAnnualRevenue();
        this.dateOfLastAnnualReport = legalEntityDto.getDateOfLastAnnualReport();
        this.parentEntityId = legalEntityDto.getParentEntityId() != null ? new LegalEntityBasicResponse(legalEntityDto.getParentEntityId()) : null;
        this.notes = legalEntityDto.getNotes();
        this.status = legalEntityDto.getStatus();
        this.createdAt = legalEntityDto.getCreatedAt();
        this.updatedAt = legalEntityDto.getUpdatedAt();
        this.owner = legalEntityDto.getOwner();
        this.entityExperience = legalEntityDto.getEntityExperience();
        this.entityFico = legalEntityDto.getEntityFico();
        this.authorizedSignerGovernmentIdCopy = legalEntityDto.getAuthorizedSignerGovernmentIdCopy();
        this.authorizedSignerGovernmentIdCopyFileName = legalEntityDto.getAuthorizedSignerGovernmentIdCopyFileName();
        if (legalEntityDto.getOwners() != null) {
            for (OwnerShipLegalEntityDto owner1 : legalEntityDto.getOwners()) {
                owners.add(new OwnerShipLegalEntityBasicResponse(owner1));
            }
        }
    }

}