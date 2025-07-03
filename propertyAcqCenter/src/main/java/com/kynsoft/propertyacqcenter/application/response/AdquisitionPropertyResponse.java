package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import java.time.LocalDate;
import lombok.*;

import java.time.LocalDateTime;
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
    private AdquisitionDocumentResponse uploadGovernmentIssuedId;
    private AdquisitionDocumentResponse hoaApplicationForm;
    private AdquisitionDocumentResponse hoaApplicationUpload;
    private AdquisitionDocumentResponse hoaFinancials;
    private AdquisitionDocumentResponse hoaRulesRegulations;
    private AdquisitionDocumentResponse buyerCarRegistration;
    private AdquisitionDocumentResponse buyerBackgroundCheck;
    private AdquisitionDocumentResponse commitmentLetter;
    private AdquisitionDocumentResponse appraisalReport;
    private AdquisitionDocumentResponse inspectionReport;
    private AdquisitionDocumentResponse sellerDisclosureForm;
    private AdquisitionDocumentResponse surveyDocument;
    private AdquisitionDocumentResponse titleCommitment;
    private AdquisitionDocumentResponse legalEntityCertificationStatus;
    private AdquisitionDocumentResponse assignmentOfContract;
    private AdquisitionDocumentResponse ownerExecutedContract;
    private AdquisitionDocumentResponse contractAddendum;
    private AdquisitionDocumentResponse finalSettlementStatement;
    private AdquisitionDocumentResponse bankStatementRequest;
    private AdquisitionDocumentResponse warrantyDeed;
    private AdquisitionDocumentResponse titleInsurance;
    private AdquisitionDocumentResponse executedClosingDocuments;

    private TitleCompanyAdquisitionPropertyResponse titleCompany;
    private SellerAdquisitionPropertyResponse seller;

    private LegalEntityBasicResponse buyer;
    private PropertiesBasicResponse property;
    private CompanyContactResponse contact;
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

        this.uploadGovernmentIssuedId = DocumentMapper.mapDocumentField(dto.getUploadGovernmentIssuedId());
        this.hoaApplicationForm = DocumentMapper.mapDocumentField(dto.getHoaApplicationForm());
        this.hoaApplicationUpload = DocumentMapper.mapDocumentField(dto.getHoaApplicationUpload());
        this.hoaFinancials = DocumentMapper.mapDocumentField(dto.getHoaFinancials());
        this.hoaRulesRegulations = DocumentMapper.mapDocumentField(dto.getHoaRulesRegulations());
        this.buyerCarRegistration = DocumentMapper.mapDocumentField(dto.getBuyerCarRegistration());
        this.buyerBackgroundCheck = DocumentMapper.mapDocumentField(dto.getBuyerBackgroundCheck());
        this.commitmentLetter = DocumentMapper.mapDocumentField(dto.getCommitmentLetter());
        this.appraisalReport = DocumentMapper.mapDocumentField(dto.getAppraisalReport());
        this.inspectionReport = DocumentMapper.mapDocumentField(dto.getInspectionReport());
        this.sellerDisclosureForm = DocumentMapper.mapDocumentField(dto.getSellerDisclosureForm());
        this.surveyDocument = DocumentMapper.mapDocumentField(dto.getSurveyDocument());
        this.titleCommitment = DocumentMapper.mapDocumentField(dto.getTitleCommitment());
        this.legalEntityCertificationStatus = DocumentMapper.mapDocumentField(dto.getLegalEntityCertificationStatus());
        this.assignmentOfContract = DocumentMapper.mapDocumentField(dto.getAssignmentOfContract());
        this.ownerExecutedContract = DocumentMapper.mapDocumentField(dto.getOwnerExecutedContract());
        this.contractAddendum = DocumentMapper.mapDocumentField(dto.getContractAddendum());
        this.finalSettlementStatement = DocumentMapper.mapDocumentField(dto.getFinalSettlementStatement());
        this.bankStatementRequest = DocumentMapper.mapDocumentField(dto.getBankStatementRequest());
        this.warrantyDeed = DocumentMapper.mapDocumentField(dto.getWarrantyDeed());
        this.titleInsurance = DocumentMapper.mapDocumentField(dto.getTitleInsurance());
        this.executedClosingDocuments = DocumentMapper.mapDocumentField(dto.getExecutedClosingDocuments());

        this.titleCompany = dto.getTitleCompany() != null ? new TitleCompanyAdquisitionPropertyResponse(dto.getTitleCompany()) : null;
        this.seller = dto.getSeller() != null ? new SellerAdquisitionPropertyResponse(dto.getSeller()) : null;

        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

}