package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.GeneralDocumentDto;
import java.time.LocalDate;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyResponse implements IResponse {

    private UUID id;
    private String buyerNameAndYearVehicle;
    private String buyerLicenseTagNo;

    private LocalDate dateAndTimeForInspections;
    private String instructionsForAccess;
    private LocalDate hoaBuyerInterviewDate;
    private LocalDate preferredMoveinDate;
    private String eSignAuthorization;
    private LocalDate finalWalkthroughDate;
    private String wireAccountHolderName;
    private String wireAccountNumber;
    private String wireRoutingNumber;
    private String zelleEmailorPhone;
    private String electricProviderConfirmation;
    private String gasServiceConfirmation;
    private String trashServiceConfirmation;
    private String waterSewerSetupConfirmation;

    private LegalEntityBasicResponse buyer;
    private PropertiesBasicResponse property;
    private CompanyContactResponse contact;
    private List<GeneralDocumentDto> documents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public AdquisitionPropertyResponse(AdquisitionPropertyDto dto) {
        this.id = dto.getId();
        this.buyer = dto.getBuyer() != null ? new LegalEntityBasicResponse(dto.getBuyer()) : null;
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.contact = dto.getContact() != null ? new CompanyContactResponse(dto.getContact()) : null;
        this.buyerNameAndYearVehicle = dto.getBuyerNameAndYearVehicle();
        this.buyerLicenseTagNo = dto.getBuyerLicenseTagNo();

        this.dateAndTimeForInspections = dto.getDateAndTimeForInspections();
        this.instructionsForAccess = dto.getInstructionsForAccess();
        this.hoaBuyerInterviewDate = dto.getHoaBuyerInterviewDate();
        this.preferredMoveinDate = dto.getPreferredMoveinDate();
        this.eSignAuthorization = dto.getESignAuthorization();
        this.finalWalkthroughDate = dto.getFinalWalkthroughDate();
        this.wireAccountHolderName = dto.getWireAccountHolderName();
        this.wireAccountNumber = dto.getWireAccountNumber();
        this.wireRoutingNumber = dto.getWireRoutingNumber();
        this.zelleEmailorPhone = dto.getZelleEmailorPhone();
        this.electricProviderConfirmation = dto.getElectricProviderConfirmation();
        this.gasServiceConfirmation = dto.getGasServiceConfirmation();
        this.trashServiceConfirmation = dto.getTrashServiceConfirmation();
        this.waterSewerSetupConfirmation = dto.getWaterSewerSetupConfirmation();
        this.documents = dto.getDocuments();

        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

}