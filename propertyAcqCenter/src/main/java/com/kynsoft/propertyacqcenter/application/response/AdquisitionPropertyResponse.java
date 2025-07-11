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

    private String sellerFullName;
    private String sellerEntityName;
    private AdquisitionDocumentResponse sellerArticlesOfIncorporation;
    private AdquisitionDocumentResponse sellerCertificateOfGoodStanding;
    private AdquisitionDocumentResponse sellerOperatingAgreement;
    private String sellerOwnershipType;
    private AdquisitionDocumentResponse sellerResolutionToSell;

    private String sellerSocialSecurityNumber;
    private String sellerMaritalStatus;
    private AdquisitionDocumentResponse sellerGovernmentId;
    private AdquisitionDocumentResponse sellerW9Form;
    private Boolean sellerForeignSeller;
    private AdquisitionDocumentResponse sellerFirptaAffidavit;
    private String sellerWireAccountHolder;
    private String sellerWireAccountNumber;
    private String sellerWireRoutingNumber;
    private String zelleContact;

    private LocalDate titleCompanyRequestForEstoppelLetter;
    private AdquisitionDocumentResponse titleCompanyEarnestMoneyDepositConfirmation;

    private LegalEntityBasicResponse buyer;
    private PropertiesBasicResponse property;
    private CompanyContactResponse contact;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    private LocalDateTime clockCurrentDate;

    //Survey & Condition
    private Boolean surveyavailable;
    private Boolean recentImprovementsLast12Months;
    private AdquisitionDocumentResponse uploadInvoicesForImprovements;
    private String summarizePropertyCondition;
    private String discloseKnownRepairsOrDefects;
    private String listItemsNotIncludedInSale;

    //Mortgage & Liens
    private Boolean isThereAMortgage;
    private String lenderName;
    private String loanNumber;
    private Double estimatedPayoffAmount;
    private AdquisitionDocumentResponse uploadLatestMortgageStatement;
    private Boolean secondLienOrHeloc;
    private Boolean irsLiensOrJudgments;
    private AdquisitionDocumentResponse uploadTaxProrationAgreement;
    private AdquisitionDocumentResponse lienSearch;
    private Double finalAgreedSalesPrice;

    //Utilities
    private String electricProvider;
    private String electricProviderAccountNumber;
    private String waterSewerProvider;
    private String gasProvider;
    private String gasProviderAccountNumber;
    private String trashServiceProvider;
    private AdquisitionDocumentResponse uploadLatestUtilityBill;

    //Rental
    private AdquisitionDocumentResponse uploadSellersDisclosureForm;
    private AdquisitionDocumentResponse uploadTenantEstoppel;
    private AdquisitionDocumentResponse uploadRentalAgreement;

    //HOA
    private Integer hoaApprovalProcessingTime;
    private Double hoaDuesAmount;

    private AdquisitionDocumentResponse hoaQASheet;
    private AdquisitionDocumentResponse hoaEstoppelCertificate;
    private AdquisitionDocumentResponse hoaApprovalLetter;
    private Integer buildingYearBuilt;
    private Double hoaMoveInFee;
    private Boolean hoaInterviewRequired;
    private AdquisitionDocumentResponse hoaApplicationInstructions;
    private String buyersCarNameAndYear;
    private Double applicationFeesAmount;
    private LocalDate applicationFeesSentDate;
    private String rentalRestrictions;
    private Double hoaSpecialAssessmentAmount;
    private AdquisitionDocumentResponse hoaHaveReserve;
    private AdquisitionDocumentResponse hoaCOICertificate;
    private String buyersSocialSecurity;
    private AdquisitionDocumentResponse hoaW9TaxID;

    //Legal & Estate
    private AdquisitionDocumentResponse proofOfOwnershipDocument;
    private AdquisitionDocumentResponse powerOfAttorneyDocument;
    private AdquisitionDocumentResponse trustOrEstateDocuments;

    //Closing
    private String enterWireInstructions;
    private String authorizeTitleCompanyInfo;

    //Access & Inspection
    private String propertyAccessCode;
    private String timeForAccess;
    private String instructionsForInspections;
    
    //Wholesaler
    private LocalDate wsalerClosingDate;
    private AdquisitionDocumentResponse wsalerAssignmentOfContract;

    //Appraiser
    private LocalDate requestAppraisalDate;
    private LocalDate confirmedAppointmentDateTime;
    private String appraisedValue;
    private String appraiserLicenseNumber;
    private String asIsValue;
    private String asRepairedValue;
    private AdquisitionDocumentResponse inspectionPhotos;
    private String requiredRepairsNoted;
    private String valuationMethod;
    private LocalDate dateSent;
    private String reportSentTo;

    private String hoaCompanyName;
    private LocalDate hoaInterviewDateProposal;
    private String preferredClosingLocation;
    private AdquisitionDocumentResponse requireElevationCertificate;
    private AdquisitionDocumentResponse elevationCertificate;
    private String outstandingCodeViolations;
    private Boolean taxBillOrAmount;
    private AdquisitionDocumentResponse hoa4050certificationStatus;

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
        this.hoa4050certificationStatus = DocumentMapper.mapDocumentField(dto.getHoa4050certificationStatus());

        this.sellerFullName = dto.getSellerFullName();
        this.sellerEntityName = dto.getSellerEntityName();
        this.sellerArticlesOfIncorporation = DocumentMapper.mapDocumentField(dto.getSellerArticlesOfIncorporation());
        this.sellerCertificateOfGoodStanding = DocumentMapper.mapDocumentField(dto.getSellerCertificateOfGoodStanding());
        this.sellerOperatingAgreement = DocumentMapper.mapDocumentField(dto.getSellerOperatingAgreement());
        this.sellerOwnershipType = dto.getSellerOwnershipType();
        this.sellerResolutionToSell = DocumentMapper.mapDocumentField(dto.getSellerResolutionToSell());
        this.sellerSocialSecurityNumber = dto.getSellerSocialSecurityNumber();
        this.sellerMaritalStatus = dto.getSellerMaritalStatus();

        this.sellerGovernmentId = DocumentMapper.mapDocumentField(dto.getSellerGovernmentId());
        this.sellerW9Form = DocumentMapper.mapDocumentField(dto.getSellerW9Form());
        this.sellerFirptaAffidavit = DocumentMapper.mapDocumentField(dto.getSellerFirptaAffidavit());
        this.sellerForeignSeller = dto.getSellerForeignSeller();
        this.sellerWireAccountHolder = dto.getSellerWireAccountHolder();
        this.sellerWireAccountNumber = dto.getSellerWireAccountNumber();
        this.sellerWireRoutingNumber = dto.getSellerWireRoutingNumber();
        this.zelleContact = dto.getZelleContact();

        this.titleCompanyRequestForEstoppelLetter = dto.getTitleCompanyRequestForEstoppelLetter();
        this.titleCompanyEarnestMoneyDepositConfirmation = DocumentMapper.mapDocumentField(dto.getTitleCompanyEarnestMoneyDepositConfirmation());

        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.clockCurrentDate = LocalDateTime.now();

        this.surveyavailable = dto.getSurveyavailable();
        this.recentImprovementsLast12Months = dto.getRecentImprovementsLast12Months();
        this.uploadInvoicesForImprovements = DocumentMapper.mapDocumentField(dto.getUploadInvoicesForImprovements());
        this.summarizePropertyCondition = dto.getSummarizePropertyCondition();
        this.discloseKnownRepairsOrDefects = dto.getDiscloseKnownRepairsOrDefects();
        this.listItemsNotIncludedInSale = dto.getListItemsNotIncludedInSale();

        this.isThereAMortgage = dto.getIsThereAMortgage();
        this.lenderName = dto.getLenderName();
        this.loanNumber = dto.getLoanNumber();
        this.estimatedPayoffAmount = dto.getEstimatedPayoffAmount();
        this.uploadLatestMortgageStatement = DocumentMapper.mapDocumentField(dto.getUploadLatestMortgageStatement());
        this.secondLienOrHeloc = dto.getSecondLienOrHeloc();
        this.irsLiensOrJudgments = dto.getIrsLiensOrJudgments();
        this.uploadTaxProrationAgreement = DocumentMapper.mapDocumentField(dto.getUploadTaxProrationAgreement());
        this.lienSearch = DocumentMapper.mapDocumentField(dto.getLienSearch());
        this.finalAgreedSalesPrice = dto.getFinalAgreedSalesPrice();

        this.electricProvider = dto.getElectricProvider();
        this.electricProviderAccountNumber = dto.getElectricProviderAccountNumber();
        this.waterSewerProvider = dto.getWaterSewerProvider();
        this.gasProvider = dto.getGasProvider();
        this.gasProviderAccountNumber = dto.getGasProviderAccountNumber();
        this.trashServiceProvider = dto.getTrashServiceProvider();
        this.uploadLatestUtilityBill = DocumentMapper.mapDocumentField(dto.getUploadLatestUtilityBill());

        this.uploadSellersDisclosureForm = DocumentMapper.mapDocumentField(dto.getUploadSellersDisclosureForm());
        this.uploadTenantEstoppel = DocumentMapper.mapDocumentField(dto.getUploadTenantEstoppel());
        this.uploadRentalAgreement = DocumentMapper.mapDocumentField(dto.getUploadRentalAgreement());

        this.hoaApprovalProcessingTime = dto.getHoaApprovalProcessingTime();
        this.hoaDuesAmount = dto.getHoaDuesAmount();

        this.proofOfOwnershipDocument = DocumentMapper.mapDocumentField(dto.getProofOfOwnershipDocument());
        this.powerOfAttorneyDocument = DocumentMapper.mapDocumentField(dto.getPowerOfAttorneyDocument());
        this.trustOrEstateDocuments = DocumentMapper.mapDocumentField(dto.getTrustOrEstateDocuments());

        this.enterWireInstructions = dto.getEnterWireInstructions();
        this.authorizeTitleCompanyInfo = dto.getAuthorizeTitleCompanyInfo();

        this.propertyAccessCode = dto.getPropertyAccessCode();
        this.timeForAccess = dto.getTimeForAccess();
        this.instructionsForInspections = dto.getInstructionsForInspections();

        this.hoaQASheet = DocumentMapper.mapDocumentField(dto.getHoaQASheet());
        this.hoaEstoppelCertificate = DocumentMapper.mapDocumentField(dto.getHoaEstoppelCertificate());
        this.hoaApprovalLetter = DocumentMapper.mapDocumentField(dto.getHoaApprovalLetter());
        this.buildingYearBuilt = dto.getBuildingYearBuilt();
        this.hoaMoveInFee = dto.getHoaMoveInFee();
        this.hoaInterviewRequired = dto.getHoaInterviewRequired();
        this.hoaApplicationInstructions = DocumentMapper.mapDocumentField(dto.getHoaApplicationInstructions());
        this.buyersCarNameAndYear = dto.getBuyersCarNameAndYear();
        this.applicationFeesAmount = dto.getApplicationFeesAmount();
        this.applicationFeesSentDate = dto.getApplicationFeesSentDate();
        this.rentalRestrictions = dto.getRentalRestrictions();
        this.hoaSpecialAssessmentAmount = dto.getHoaSpecialAssessmentAmount();
        this.hoaHaveReserve = DocumentMapper.mapDocumentField(dto.getHoaHaveReserve());
        this.hoaCOICertificate = DocumentMapper.mapDocumentField(dto.getHoaCOICertificate());
        this.buyersSocialSecurity = dto.getBuyersSocialSecurity();
        this.hoaW9TaxID = DocumentMapper.mapDocumentField(dto.getHoaW9TaxID());

        this.wsalerClosingDate = dto.getWsalerClosingDate();
        this.wsalerAssignmentOfContract = DocumentMapper.mapDocumentField(dto.getWsalerAssignmentOfContract());

        this.requestAppraisalDate = dto.getRequestAppraisalDate();
        this.confirmedAppointmentDateTime = dto.getConfirmedAppointmentDateTime();
        this.appraisedValue = dto.getAppraisedValue();
        this.appraiserLicenseNumber = dto.getAppraiserLicenseNumber();
        this.asIsValue = dto.getAsIsValue();
        this.asRepairedValue = dto.getAsRepairedValue();
        this.inspectionPhotos = DocumentMapper.mapDocumentField(dto.getInspectionPhotos());
        this.requiredRepairsNoted = dto.getRequiredRepairsNoted();
        this.valuationMethod = dto.getValuationMethod();
        this.dateSent = dto.getDateSent();
        this.reportSentTo = dto.getReportSentTo();

        this.hoaCompanyName = dto.getHoaCompanyName();
        this.hoaInterviewDateProposal = dto.getHoaInterviewDateProposal();
        this.preferredClosingLocation = dto.getPreferredClosingLocation();
        this.requireElevationCertificate = DocumentMapper.mapDocumentField(dto.getRequireElevationCertificate());
        this.elevationCertificate = DocumentMapper.mapDocumentField(dto.getElevationCertificate());
        this.outstandingCodeViolations = dto.getOutstandingCodeViolations();
        this.taxBillOrAmount = dto.getTaxBillOrAmount();
    }

}
