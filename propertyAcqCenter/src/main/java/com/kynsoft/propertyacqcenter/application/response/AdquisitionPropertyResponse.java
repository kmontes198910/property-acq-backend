package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.application.response.adquisitionProperty.AdquisitionPropertyBuyerPropertyInformationResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import java.time.LocalDate;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private List<AdquisitionDocumentResponse> bankStatementRequest;
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
    private String hoaApprovalProcessingTime;
    private Double hoaDuesAmount;

    private AdquisitionDocumentResponse hoaQASheet;
    private AdquisitionDocumentResponse hoaEstoppelCertificate;
    private AdquisitionDocumentResponse hoaApprovalLetter;
    private Integer buildingYearBuilt;
    private Double hoaMoveInFee;
    private Boolean hoaInterviewRequired;
    private AdquisitionDocumentResponse hoaApplicationInstructions;
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
    private Boolean authorizeTitleCompanyInfo;

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
    private Boolean outstandingCodeViolations;
    private AdquisitionDocumentResponse taxBillOrAmount;
    private AdquisitionDocumentResponse hoa4050certificationStatus;
    private AdquisitionDocumentResponse sellerUploadGovernmentIssuedId;

    private String whOwnershipType;
    private String whwireAccountHolderName;
    private String whwireAccountNumber;
    private String whwireRoutingNumber;
    private String whZelleEmailorPhone;

    private Double hoaTotalUnits;
    private AdquisitionDocumentResponse hoaDeclarationOfCondominium;
    private AdquisitionDocumentResponse hoaCondominiumRider;
    private AdquisitionDocumentResponse hoaBylaws;
    private AdquisitionDocumentResponse hoaLatestApprovedBudget;
    private AdquisitionDocumentResponse hoaReserveStudy;
    private AdquisitionDocumentResponse hoaCurrentSpecialAssessmentDisclosure;
    private AdquisitionDocumentResponse hoaPendingLawsuits;
    private AdquisitionDocumentResponse hoaDelinquencyReport;
    private AdquisitionDocumentResponse hoaParkingAssignment;
    private AdquisitionDocumentResponse hoaCondoQuestionnaireForm;
    private AdquisitionDocumentResponse buyerCreditReport;
    private String hoaValidatorWebsite;
    private String hoaApplicationLink;

    private AdquisitionDocumentResponse buyerProofOfFunds;
    private String buyerCarBrand;
    private Double buyerCarYear;
    private AdquisitionDocumentResponse buyerDriverLicense;
    private AdquisitionDocumentResponse buyerCarInsurance;

    private String buyerPersonalAccountHolderName;
    private String buyerPersonalAccountNumber;
    private String buyerPersonalRoutingNumber;
    private String buyerPersonalZelleEmailorPhone;
    private List<AdquisitionDocumentResponse> buyerPersonalBankStatements;
    private String buyerPersonalBankName;
    private Boolean buyerPersonalUseForHoaBankReference;
    private Boolean buyerPersonalUseForLenderBankReference;
    private Boolean buyerVoidCheck;
    private Boolean buyerLegalEntityUseForHoaBankReference;

    private String buyerElectricProviderAccount;
    private String buyerGasServiceAccount;
    private String buyerTrashServiceAccount;
    private String buyerWaterSewerSetupAccount;
    private String buyerInternetService;
    private String buyerNotes;
    private LocalDate buyerStartServiceDate;
    private Double buyerDepositAmount;

    private LocalDate gasBuyerStartServiceDate;
    private Double gasBuyerDepositAmount;
    private LocalDate trashBuyerStartServiceDate;
    private Double trashBuyerDepositAmount;
    private LocalDate waterBuyerStartServiceDate;
    private Double waterBuyerDepositAmount;
    private LocalDate internetBuyerStartServiceDate;
    private Double internetBuyerDepositAmount;
    private String buyerBankName;
    private Boolean buyerPersonalVoidCheck;
    private LocalDate originalContractClosingDate;
    private String buyerMaritalStatus;

    //Seller
    private String sellerPersonalAccountHolderName;
    private String sellerPersonalAccountNumber;
    private String sellerPersonalRoutingNumber;
    private String sellerPersonalZelleEmailorPhone;
    private String sellerPersonalBankName;
    private List<AdquisitionDocumentResponse> sellerBankStatementRequest;//array
    private List<AdquisitionDocumentResponse> sellerPersonalBankStatements;//array
    private Boolean sellerVoidCheck;
    private Boolean sellerPersonalVoidCheck;

    private BankAccountAdquisitionPropertyResponse buyerBankAccount;
    private BankAccountAdquisitionPropertyResponse sellerBankAccount;

    private AdquisitionDocumentResponse hoaInpectionReport;
    private AdquisitionDocumentResponse hoaElectricalReport;
    private AdquisitionDocumentResponse hoaHvacReport;
    private AdquisitionDocumentResponse hoaRoofReport;
    private AdquisitionDocumentResponse hoaStructuralReport;
    private AdquisitionDocumentResponse hoaPlumbingReport;
    private String hoaNotesReport;
    private AdquisitionDocumentResponse hoaOthersReport;
    private String hoaNotes;

    private String employerReferenceName;
    private String employerReferencePhone;
    private String employerReferenceEmail;
    private String employerReferencePosition;

    private String bankReferenceName;
    private String bankReferencePhone;
    private String bankReferenceEmail;
    private String bankReferencePosition;

    private String personalReferenceName;
    private String personalReferencePhone;
    private String personalReferenceEmail;
    private String personalReferencePosition;

    private AdquisitionDocumentResponse executeHud;
    private Double buyerRepairBudget;
    private AdquisitionDocumentResponse buyerApprovedPlans;//File
    private AdquisitionDocumentResponse buyerPermits;//File
    private AdquisitionPropertyBuyerPropertyInformationResponse lender;

    public AdquisitionPropertyResponse(AdquisitionPropertyDto dto) {
        this.id = dto.getId();
        this.buyer = dto.getBuyer() != null ? new LegalEntityBasicResponse(dto.getBuyer()) : null;
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.contact = dto.getContact() != null ? new CompanyContactResponse(dto.getContact()) : null;
        this.buyerBankAccount = dto.getBuyerBankAccount() != null ? new BankAccountAdquisitionPropertyResponse(dto.getBuyerBankAccount()) : null;
        this.sellerBankAccount = dto.getSellerBankAccount() != null ? new BankAccountAdquisitionPropertyResponse(dto.getSellerBankAccount()) : null;
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
        this.bankStatementRequest = convertDbToList(dto.getBankStatementRequest());
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
        this.taxBillOrAmount = DocumentMapper.mapDocumentField(dto.getTaxBillOrAmount());
        this.sellerUploadGovernmentIssuedId = DocumentMapper.mapDocumentField(dto.getSellerUploadGovernmentIssuedId());

        this.whOwnershipType = dto.getWhOwnershipType();
        this.whwireAccountHolderName = dto.getWhwireAccountHolderName();
        this.whwireAccountNumber = dto.getWhwireAccountNumber();
        this.whwireRoutingNumber = dto.getWhwireRoutingNumber();
        this.whZelleEmailorPhone = dto.getWhZelleEmailorPhone();

        this.hoaTotalUnits = dto.getHoaTotalUnits();
        this.hoaDeclarationOfCondominium = DocumentMapper.mapDocumentField(dto.getHoaDeclarationOfCondominium());
        this.hoaCondominiumRider = DocumentMapper.mapDocumentField(dto.getHoaCondominiumRider());
        this.hoaBylaws = DocumentMapper.mapDocumentField(dto.getHoaBylaws());
        this.hoaLatestApprovedBudget = DocumentMapper.mapDocumentField(dto.getHoaLatestApprovedBudget());
        this.hoaReserveStudy = DocumentMapper.mapDocumentField(dto.getHoaReserveStudy());
        this.hoaCurrentSpecialAssessmentDisclosure = DocumentMapper.mapDocumentField(dto.getHoaCurrentSpecialAssessmentDisclosure());
        this.hoaPendingLawsuits = DocumentMapper.mapDocumentField(dto.getHoaPendingLawsuits());
        this.hoaDelinquencyReport = DocumentMapper.mapDocumentField(dto.getHoaDelinquencyReport());
        this.hoaParkingAssignment = DocumentMapper.mapDocumentField(dto.getHoaParkingAssignment());
        this.hoaCondoQuestionnaireForm = DocumentMapper.mapDocumentField(dto.getHoaCondoQuestionnaireForm());
        this.buyerCreditReport = DocumentMapper.mapDocumentField(dto.getBuyerCreditReport());
        this.hoaValidatorWebsite = dto.getHoaValidatorWebsite();
        this.hoaApplicationLink = dto.getHoaApplicationLink();

        this.buyerProofOfFunds = DocumentMapper.mapDocumentField(dto.getBuyerProofOfFunds());
        this.buyerCarBrand = dto.getBuyerCarBrand();
        this.buyerCarYear = dto.getBuyerCarYear();
        this.buyerDriverLicense = DocumentMapper.mapDocumentField(dto.getBuyerDriverLicense());
        this.buyerCarInsurance = DocumentMapper.mapDocumentField(dto.getBuyerCarInsurance());

        this.buyerPersonalAccountHolderName = dto.getBuyerPersonalAccountHolderName();
        this.buyerPersonalAccountNumber = dto.getBuyerPersonalAccountNumber();
        this.buyerPersonalRoutingNumber = dto.getBuyerPersonalRoutingNumber();
        this.buyerPersonalZelleEmailorPhone = dto.getBuyerPersonalZelleEmailorPhone();
        this.buyerPersonalBankStatements = convertDbToList(dto.getBuyerPersonalBankStatements());
        //this.buyerPersonalBankStatements = DocumentMapper.mapDocumentField(dto.getBuyerPersonalBankStatements());
        this.buyerPersonalBankName = dto.getBuyerPersonalBankName();
        this.buyerPersonalUseForHoaBankReference = dto.getBuyerPersonalUseForHoaBankReference();
        this.buyerPersonalUseForLenderBankReference = dto.getBuyerPersonalUseForLenderBankReference();
        this.buyerVoidCheck = dto.getBuyerVoidCheck();
        this.buyerLegalEntityUseForHoaBankReference = dto.getBuyerLegalEntityUseForHoaBankReference();

        this.buyerElectricProviderAccount = dto.getBuyerElectricProviderAccount();
        this.buyerGasServiceAccount = dto.getBuyerGasServiceAccount();
        this.buyerTrashServiceAccount = dto.getBuyerTrashServiceAccount();
        this.buyerWaterSewerSetupAccount = dto.getBuyerWaterSewerSetupAccount();
        this.buyerInternetService = dto.getBuyerInternetService();
        this.buyerNotes = dto.getBuyerNotes();
        this.buyerStartServiceDate = dto.getBuyerStartServiceDate();
        this.buyerDepositAmount = dto.getBuyerDepositAmount();

        this.gasBuyerStartServiceDate = dto.getGasBuyerStartServiceDate();
        this.gasBuyerDepositAmount = dto.getGasBuyerDepositAmount();
        this.trashBuyerStartServiceDate = dto.getTrashBuyerStartServiceDate();
        this.trashBuyerDepositAmount = dto.getTrashBuyerDepositAmount();
        this.waterBuyerStartServiceDate = dto.getWaterBuyerStartServiceDate();
        this.waterBuyerDepositAmount = dto.getWaterBuyerDepositAmount();
        this.internetBuyerStartServiceDate = dto.getInternetBuyerStartServiceDate();
        this.internetBuyerDepositAmount = dto.getInternetBuyerDepositAmount();
        this.buyerBankName = dto.getBuyerBankName();
        this.buyerPersonalVoidCheck = dto.getBuyerPersonalVoidCheck();
        this.originalContractClosingDate = dto.getOriginalContractClosingDate();
        this.buyerMaritalStatus = dto.getBuyerMaritalStatus();

        this.sellerPersonalAccountHolderName = dto.getSellerPersonalAccountHolderName();
        this.sellerPersonalAccountNumber = dto.getSellerPersonalAccountNumber();
        this.sellerPersonalRoutingNumber = dto.getSellerPersonalRoutingNumber();
        this.sellerPersonalZelleEmailorPhone = dto.getSellerPersonalZelleEmailorPhone();
        this.sellerPersonalBankName = dto.getSellerPersonalBankName();
        this.sellerBankStatementRequest = convertDbToList(dto.getSellerBankStatementRequest());
        this.sellerPersonalBankStatements = convertDbToList(dto.getSellerPersonalBankStatements());
        this.sellerVoidCheck = dto.getSellerVoidCheck();
        this.sellerPersonalVoidCheck = dto.getSellerPersonalVoidCheck();

        this.hoaInpectionReport = DocumentMapper.mapDocumentField(dto.getHoaInpectionReport());
        this.hoaElectricalReport = DocumentMapper.mapDocumentField(dto.getHoaElectricalReport());
        this.hoaHvacReport = DocumentMapper.mapDocumentField(dto.getHoaHvacReport());
        this.hoaRoofReport = DocumentMapper.mapDocumentField(dto.getHoaRoofReport());
        this.hoaStructuralReport = DocumentMapper.mapDocumentField(dto.getHoaStructuralReport());
        this.hoaPlumbingReport = DocumentMapper.mapDocumentField(dto.getHoaPlumbingReport());
        this.hoaNotesReport = dto.getHoaNotesReport();
        this.hoaOthersReport = DocumentMapper.mapDocumentField(dto.getHoaOthersReport());
        this.hoaNotes = dto.getHoaNotes();

        this.employerReferenceName = dto.getEmployerReferenceName();
        this.employerReferencePhone = dto.getEmployerReferencePhone();
        this.employerReferenceEmail = dto.getEmployerReferenceEmail();
        this.employerReferencePosition = dto.getEmployerReferencePosition();
        this.bankReferenceName = dto.getBankReferenceName();
        this.bankReferencePhone = dto.getBankReferencePhone();
        this.bankReferenceEmail = dto.getBankReferenceEmail();
        this.bankReferencePosition = dto.getBankReferencePosition();
        this.personalReferenceName = dto.getPersonalReferenceName();
        this.personalReferencePhone = dto.getPersonalReferencePhone();
        this.personalReferenceEmail = dto.getPersonalReferenceEmail();
        this.personalReferencePosition = dto.getPersonalReferencePosition();

        this.executeHud = DocumentMapper.mapDocumentField(dto.getExecuteHud());
        this.buyerRepairBudget = dto.getLender() != null ? dto.getLender().getBuyerRepairBudget() : null;
        this.buyerApprovedPlans = dto.getLender() != null ? DocumentMapper.mapDocumentField(dto.getLender().getBuyerApprovedPlans()) : null;
        this.buyerPermits = dto.getLender() != null ? DocumentMapper.mapDocumentField(dto.getLender().getBuyerPermits()) : null;
        this.lender = dto.getLender() != null ? new AdquisitionPropertyBuyerPropertyInformationResponse(dto.getLender()) : null;
    }

    private List<AdquisitionDocumentResponse> convertDbToList(String dbData) {
        List<AdquisitionDocumentResponse> result = new ArrayList<>();
        if (dbData == null || dbData.isEmpty()) {
            return result;
        }

        String[] parts = dbData.split("\\|");
        // Asumimos que los datos vienen en pares fileName, filePath
        for (int i = 0; i < parts.length; i += 2) {
            if (i + 1 < parts.length) {
                AdquisitionDocumentResponse request = new AdquisitionDocumentResponse();
                request.setFileName(parts[i]);
                request.setFilePath(parts[i + 1]);
                result.add(request);
            }
        }
        return result;
    }

}
