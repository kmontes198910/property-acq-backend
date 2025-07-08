package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateAdquisitionPropertyCommand implements ICommand {

    private UUID id;
    private UUID buyer;
    private String property;
    private UUID contact;

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
    private CreateDocumentRequest uploadGovernmentIssuedId;
    private CreateDocumentRequest hoaApplicationForm;
    private CreateDocumentRequest hoaApplicationUpload;
    private CreateDocumentRequest hoaFinancials;
    private CreateDocumentRequest hoaRulesRegulations;
    private CreateDocumentRequest buyerCarRegistration;
    private CreateDocumentRequest buyerBackgroundCheck;
    private CreateDocumentRequest commitmentLetter;
    private CreateDocumentRequest appraisalReport;
    private CreateDocumentRequest inspectionReport;
    private CreateDocumentRequest sellerDisclosureForm;
    private CreateDocumentRequest surveyDocument;
    private CreateDocumentRequest titleCommitment;
    private CreateDocumentRequest legalEntityCertificationStatus;
    private CreateDocumentRequest assignmentOfContract;
    private CreateDocumentRequest ownerExecutedContract;
    private CreateDocumentRequest contractAddendum;
    private CreateDocumentRequest finalSettlementStatement;
    private CreateDocumentRequest bankStatementRequest;
    private CreateDocumentRequest warrantyDeed;
    private CreateDocumentRequest titleInsurance;
    private CreateDocumentRequest executedClosingDocuments;

    private String buyerFullLegalName;
    private String buyerContactEmail;
    private String buyerEntityName;
    private String buyerMailingAddress;
    private String buyerMobilePhoneNumber;
    private String hoa4050certificationStatus;
    private String hoaValidatorContactName;
    private String hoaValidatorEmail;
    private String hoaValidatorPhoneNumber;
    private String closingCountdownClock;
    private LocalDate contractClosingDate;

    //Seller
    private String sellerFullName;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private String sellerEntityName;//Sellers, Wholesaler, Real Estate Agent
    private CreateDocumentRequest sellerArticlesOfIncorporation;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private CreateDocumentRequest sellerCertificateOfGoodStanding;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private CreateDocumentRequest sellerOperatingAgreement;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private String sellerOwnershipType;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private CreateDocumentRequest sellerResolutionToSell;

    // Información personal
    private String sellerSocialSecurityNumber;
    private String sellerMaritalStatus;
    private CreateDocumentRequest sellerGovernmentId;
    private CreateDocumentRequest sellerW9Form;

    // Información FIRPTA
    private Boolean sellerForeignSeller;
    private CreateDocumentRequest sellerFirptaAffidavit;

    // Información bancaria
    private String sellerWireAccountHolder;
    private String sellerWireAccountNumber;
    private String sellerWireRoutingNumber;
    private String zelleContact;

    //Title Company
    private LocalDate titleCompanyRequestForEstoppelLetter;
    private CreateDocumentRequest titleCompanyEarnestMoneyDepositConfirmation;

    //Survey & Condition
    private Boolean surveyavailable;
    private String recentImprovementsLast12Months;
    private CreateDocumentRequest uploadInvoicesForImprovements;
    private String summarizePropertyCondition;
    private String discloseKnownRepairsOrDefects;
    private String listItemsNotIncludedInSale;

    //Mortgage & Liens
    private Boolean isThereAMortgage;
    private String lenderName;
    private String loanNumber;
    private Double estimatedPayoffAmount;
    private CreateDocumentRequest uploadLatestMortgageStatement;
    private Boolean secondLienOrHeloc;
    private Boolean irsLiensOrJudgments;
    private CreateDocumentRequest uploadTaxProrationAgreement;
    private CreateDocumentRequest lienSearch;
    private Double finalAgreedSalesPrice;

    //Utilities
    private String electricProvider;
    private String electricProviderAccountNumber;
    private String waterSewerProvider;
    private String gasProvider;
    private String gasProviderAccountNumber;
    private String trashServiceProvider;
    private CreateDocumentRequest uploadLatestUtilityBill;

    //Rental
    private CreateDocumentRequest uploadSellersDisclosureForm;
    private CreateDocumentRequest uploadTenantEstoppel;
    private CreateDocumentRequest uploadRentalAgreement;

    //HOA
    private Integer hoaApprovalProcessingTime;
    private Double hoaDuesAmount;

    private CreateDocumentRequest hoaQASheet;
    private CreateDocumentRequest hoaEstoppelCertificate;
    private CreateDocumentRequest hoaApprovalLetter;
    private Integer buildingYearBuilt;
    private Double hoaMoveInFee;
    private Boolean hoaInterviewRequired;
    private CreateDocumentRequest hoaApplicationInstructions;
    private String buyersCarNameAndYear;
    private Double applicationFeesAmount;
    private LocalDate applicationFeesSentDate;
    private String rentalRestrictions;
    private Double hoaSpecialAssessmentAmount;
    private CreateDocumentRequest hoaHaveReserve;
    private CreateDocumentRequest hoaCOICertificate;
    private String buyersSocialSecurity;
    private CreateDocumentRequest hoaW9TaxID;

    //Legal & Estate
    private CreateDocumentRequest proofOfOwnershipDocument;
    private CreateDocumentRequest powerOfAttorneyDocument;
    private CreateDocumentRequest trustOrEstateDocuments;

    //Closing
    private String enterWireInstructions;
    private String authorizeTitleCompanyInfo;

    //Access & Inspection
    private String propertyAccessCode;
    private String timeForAccess;
    private String instructionsForInspections;

    //Wholesaler
    private LocalDate wsalerClosingDate;
    private CreateDocumentRequest wsalerAssignmentOfContract;

    //Appraiser
    private LocalDate requestAppraisalDate;
    private LocalDate confirmedAppointmentDateTime;
    private String appraisedValue;
    private String appraiserLicenseNumber;
    private String asIsValue;
    private String asRepairedValue;
    private CreateDocumentRequest inspectionPhotos;
    private String requiredRepairsNoted;
    private String valuationMethod;
    private LocalDate dateSent;
    private String reportSentTo;

    public UpdateAdquisitionPropertyCommand(UUID id, UUID buyer, String property, UUID contact, String buyerNameAndYearVehicle,
            String buyerLicenseTagNo,
            LocalDate dateAndTimeForInspections, String instructionsForAccess, LocalDate hoaBuyerInterviewDate,
            LocalDate preferredMoveinDate, String eSignAuthorization, LocalDate finalWalkthroughDate,
            String wireAccountHolderName, String wireAccountNumber, String wireRoutingNumber,
            String zelleEmailorPhone, String electricProviderConfirmation, String gasServiceConfirmation,
            String trashServiceConfirmation, String waterSewerSetupConfirmation,
            CreateDocumentRequest uploadGovernmentIssuedId, CreateDocumentRequest hoaApplicationForm,
            CreateDocumentRequest hoaApplicationUpload, CreateDocumentRequest hoaFinancials,
            CreateDocumentRequest hoaRulesRegulations, CreateDocumentRequest buyerCarRegistration,
            CreateDocumentRequest buyerBackgroundCheck, CreateDocumentRequest commitmentLetter,
            CreateDocumentRequest appraisalReport, CreateDocumentRequest inspectionReport,
            CreateDocumentRequest sellerDisclosureForm, CreateDocumentRequest surveyDocument,
            CreateDocumentRequest titleCommitment, CreateDocumentRequest legalEntityCertificationStatus,
            CreateDocumentRequest assignmentOfContract, CreateDocumentRequest ownerExecutedContract,
            CreateDocumentRequest contractAddendum, CreateDocumentRequest finalSettlementStatement,
            CreateDocumentRequest bankStatementRequest, CreateDocumentRequest warrantyDeed, CreateDocumentRequest titleInsurance,
            CreateDocumentRequest executedClosingDocuments, String buyerFullLegalName, String buyerContactEmail,
            String buyerEntityName, String buyerMailingAddress, String buyerMobilePhoneNumber, String hoa4050certificationStatus,
            String hoaValidatorContactName, String hoaValidatorEmail, String hoaValidatorPhoneNumber,
            String closingCountdownClock, LocalDate contractClosingDate,
            String sellerFullName, String sellerEntityName, CreateDocumentRequest sellerArticlesOfIncorporation,
            CreateDocumentRequest sellerCertificateOfGoodStanding, CreateDocumentRequest sellerOperatingAgreement,
            String sellerOwnershipType, CreateDocumentRequest sellerResolutionToSell, String sellerSocialSecurityNumber,
            String sellerMaritalStatus, CreateDocumentRequest sellerGovernmentId, CreateDocumentRequest sellerW9Form,
            Boolean sellerForeignSeller, CreateDocumentRequest sellerFirptaAffidavit, String sellerWireAccountHolder,
            String sellerWireAccountNumber, String sellerWireRoutingNumber, String zelleContact, LocalDate titleCompanyRequestForEstoppelLetter,
            CreateDocumentRequest titleCompanyEarnestMoneyDepositConfirmation,
            // Survey & Condition
            Boolean surveyavailable,
            String recentImprovementsLast12Months,
            CreateDocumentRequest uploadInvoicesForImprovements,
            String summarizePropertyCondition,
            String discloseKnownRepairsOrDefects,
            String listItemsNotIncludedInSale,
            // Mortgage & Liens
            Boolean isThereAMortgage,
            String lenderName,
            String loanNumber,
            Double estimatedPayoffAmount,
            CreateDocumentRequest uploadLatestMortgageStatement,
            Boolean secondLienOrHeloc,
            Boolean irsLiensOrJudgments,
            CreateDocumentRequest uploadTaxProrationAgreement,
            String electricProvider,
            String electricProviderAccountNumber,
            String waterSewerProvider,
            String gasProvider,
            String gasProviderAccountNumber,
            String trashServiceProvider,
            CreateDocumentRequest uploadLatestUtilityBill,
            CreateDocumentRequest uploadSellersDisclosureForm,
            CreateDocumentRequest uploadTenantEstoppel,
            CreateDocumentRequest uploadRentalAgreement,
            Integer hoaApprovalProcessingTime,
            Double hoaDuesAmount,
            CreateDocumentRequest proofOfOwnershipDocument,
            CreateDocumentRequest powerOfAttorneyDocument,
            CreateDocumentRequest trustOrEstateDocuments,
            String enterWireInstructions,
            String authorizeTitleCompanyInfo,
            String propertyAccessCode,
            String timeForAccess,
            String instructionsForInspections,
            //HOA
            CreateDocumentRequest hoaQASheet,
            CreateDocumentRequest hoaEstoppelCertificate,
            CreateDocumentRequest hoaApprovalLetter,
            Integer buildingYearBuilt,
            Double hoaMoveInFee,
            Boolean hoaInterviewRequired,
            CreateDocumentRequest hoaApplicationInstructions,
            String buyersCarNameAndYear,
            Double applicationFeesAmount,
            LocalDate applicationFeesSentDate,
            String rentalRestrictions,
            Double hoaSpecialAssessmentAmount,
            CreateDocumentRequest hoaHaveReserve,
            CreateDocumentRequest hoaCOICertificate,
            String buyersSocialSecurity,
            CreateDocumentRequest hoaW9TaxID,
            CreateDocumentRequest lienSearch,
            Double finalAgreedSalesPrice,
            LocalDate wsalerClosingDate,
            CreateDocumentRequest wsalerAssignmentOfContract,
            LocalDate requestAppraisalDate,
            LocalDate confirmedAppointmentDateTime,
            String appraisedValue,
            String appraiserLicenseNumber,
            String asIsValue,
            String asRepairedValue,
            CreateDocumentRequest inspectionPhotos,
            String requiredRepairsNoted,
            String valuationMethod,
            LocalDate dateSent,
            String reportSentTo
            ) {
        this.id = id;
        this.buyer = buyer;
        this.property = property;
        this.contact = contact;
        this.buyerLicenseTagNo = buyerLicenseTagNo;
        this.buyerNameAndYearVehicle = buyerNameAndYearVehicle;
        this.dateAndTimeForInspections = dateAndTimeForInspections;//
        this.instructionsForAccess = instructionsForAccess;//
        this.hoaBuyerInterviewDate = hoaBuyerInterviewDate;//
        this.preferredMoveinDate = preferredMoveinDate;//
        this.eSignAuthorization = eSignAuthorization;//
        this.finalWalkthroughDate = finalWalkthroughDate;//
        this.wireAccountHolderName = wireAccountHolderName;//
        this.wireAccountNumber = wireAccountNumber;//
        this.wireRoutingNumber = wireRoutingNumber;//
        this.zelleEmailorPhone = zelleEmailorPhone;//
        this.electricProviderConfirmation = electricProviderConfirmation;//
        this.gasServiceConfirmation = gasServiceConfirmation;//
        this.trashServiceConfirmation = trashServiceConfirmation;//
        this.waterSewerSetupConfirmation = waterSewerSetupConfirmation;//
        this.uploadGovernmentIssuedId = uploadGovernmentIssuedId;
        this.hoaApplicationForm = hoaApplicationForm;
        this.hoaApplicationUpload = hoaApplicationUpload;
        this.hoaFinancials = hoaFinancials;
        this.hoaRulesRegulations = hoaRulesRegulations;
        this.buyerCarRegistration = buyerCarRegistration;
        this.buyerBackgroundCheck = buyerBackgroundCheck;
        this.commitmentLetter = commitmentLetter;
        this.appraisalReport = appraisalReport;
        this.inspectionReport = inspectionReport;
        this.sellerDisclosureForm = sellerDisclosureForm;
        this.surveyDocument = surveyDocument;
        this.titleCommitment = titleCommitment;
        this.legalEntityCertificationStatus = legalEntityCertificationStatus;
        this.assignmentOfContract = assignmentOfContract;
        this.ownerExecutedContract = ownerExecutedContract;
        this.contractAddendum = contractAddendum;
        this.finalSettlementStatement = finalSettlementStatement;
        this.bankStatementRequest = bankStatementRequest;
        this.warrantyDeed = warrantyDeed;
        this.titleInsurance = titleInsurance;
        this.executedClosingDocuments = executedClosingDocuments;
        this.buyerFullLegalName = buyerFullLegalName;
        this.buyerContactEmail = buyerContactEmail;
        this.buyerEntityName = buyerEntityName;
        this.buyerMailingAddress = buyerMailingAddress;
        this.buyerMobilePhoneNumber = buyerMobilePhoneNumber;
        this.hoa4050certificationStatus = hoa4050certificationStatus;
        this.hoaValidatorContactName = hoaValidatorContactName;
        this.hoaValidatorEmail = hoaValidatorEmail;
        this.hoaValidatorPhoneNumber = hoaValidatorPhoneNumber;
        this.closingCountdownClock = closingCountdownClock;
        this.contractClosingDate = contractClosingDate;
        this.sellerFullName = sellerFullName;
        this.sellerEntityName = sellerEntityName;
        this.sellerArticlesOfIncorporation = sellerArticlesOfIncorporation;
        this.sellerCertificateOfGoodStanding = sellerCertificateOfGoodStanding;
        this.sellerOperatingAgreement = sellerOperatingAgreement;
        this.sellerOwnershipType = sellerOwnershipType;
        this.sellerResolutionToSell = sellerResolutionToSell;
        this.sellerSocialSecurityNumber = sellerSocialSecurityNumber;
        this.sellerMaritalStatus = sellerMaritalStatus;
        this.sellerGovernmentId = sellerGovernmentId;
        this.sellerW9Form = sellerW9Form;
        this.sellerForeignSeller = sellerForeignSeller;
        this.sellerFirptaAffidavit = sellerFirptaAffidavit;
        this.sellerWireAccountHolder = sellerWireAccountHolder;
        this.sellerWireAccountNumber = sellerWireAccountNumber;
        this.sellerWireRoutingNumber = sellerWireRoutingNumber;
        this.zelleContact = zelleContact;
        this.titleCompanyRequestForEstoppelLetter = titleCompanyRequestForEstoppelLetter;
        this.titleCompanyEarnestMoneyDepositConfirmation = titleCompanyEarnestMoneyDepositConfirmation;

        this.surveyavailable = surveyavailable;
        this.recentImprovementsLast12Months = recentImprovementsLast12Months;
        this.uploadInvoicesForImprovements = uploadInvoicesForImprovements;
        this.summarizePropertyCondition = summarizePropertyCondition;
        this.discloseKnownRepairsOrDefects = discloseKnownRepairsOrDefects;
        this.listItemsNotIncludedInSale = listItemsNotIncludedInSale;

        // Mortgage & Liens
        this.isThereAMortgage = isThereAMortgage;
        this.lenderName = lenderName;
        this.loanNumber = loanNumber;
        this.estimatedPayoffAmount = estimatedPayoffAmount;
        this.uploadLatestMortgageStatement = uploadLatestMortgageStatement;
        this.secondLienOrHeloc = secondLienOrHeloc;
        this.irsLiensOrJudgments = irsLiensOrJudgments;
        this.uploadTaxProrationAgreement = uploadTaxProrationAgreement;

        //Utilities
        this.electricProvider = electricProvider;
        this.electricProviderAccountNumber = electricProviderAccountNumber;
        this.waterSewerProvider = waterSewerProvider;
        this.gasProvider = gasProvider;
        this.gasProviderAccountNumber = gasProviderAccountNumber;
        this.trashServiceProvider = trashServiceProvider;
        this.uploadLatestUtilityBill = uploadLatestUtilityBill;

        //Rental
        this.uploadSellersDisclosureForm = uploadSellersDisclosureForm;
        this.uploadTenantEstoppel = uploadTenantEstoppel;
        this.uploadRentalAgreement = uploadRentalAgreement;

        //HOA
        this.hoaApprovalProcessingTime = hoaApprovalProcessingTime;
        this.hoaDuesAmount = hoaDuesAmount;
        this.hoaQASheet = hoaQASheet;
        this.hoaEstoppelCertificate = hoaEstoppelCertificate;
        this.hoaApprovalLetter = hoaApprovalLetter;
        this.buildingYearBuilt = buildingYearBuilt;
        this.hoaMoveInFee = hoaMoveInFee;
        this.hoaInterviewRequired = hoaInterviewRequired;
        this.hoaApplicationInstructions = hoaApplicationInstructions;
        this.buyersCarNameAndYear = buyersCarNameAndYear;
        this.applicationFeesAmount = applicationFeesAmount;
        this.applicationFeesSentDate = applicationFeesSentDate;
        this.rentalRestrictions = rentalRestrictions;
        this.hoaSpecialAssessmentAmount = hoaSpecialAssessmentAmount;
        this.hoaHaveReserve = hoaHaveReserve;
        this.hoaCOICertificate = hoaCOICertificate;
        this.buyersSocialSecurity = buyersSocialSecurity;
        this.hoaW9TaxID = hoaW9TaxID;

        //Legal & Estate
        this.proofOfOwnershipDocument = proofOfOwnershipDocument;
        this.powerOfAttorneyDocument = powerOfAttorneyDocument;
        this.trustOrEstateDocuments = trustOrEstateDocuments;

        //Closing
        this.enterWireInstructions = enterWireInstructions;
        this.authorizeTitleCompanyInfo = authorizeTitleCompanyInfo;

        this.propertyAccessCode = propertyAccessCode;
        this.timeForAccess = timeForAccess;
        this.instructionsForInspections = instructionsForInspections;
        this.lienSearch = lienSearch;
        this.finalAgreedSalesPrice = finalAgreedSalesPrice;

        this.wsalerClosingDate = wsalerClosingDate;
        this.wsalerAssignmentOfContract = wsalerAssignmentOfContract;

        this.requestAppraisalDate = requestAppraisalDate;
        this.confirmedAppointmentDateTime = confirmedAppointmentDateTime;
        this.appraisedValue = appraisedValue;
        this.appraiserLicenseNumber = appraiserLicenseNumber;
        this.asIsValue = asIsValue;
        this.asRepairedValue = asRepairedValue;
        this.inspectionPhotos = inspectionPhotos;
        this.requiredRepairsNoted = requiredRepairsNoted;
        this.valuationMethod = valuationMethod;
        this.dateSent = dateSent;
        this.reportSentTo = reportSentTo;
    }

    public static UpdateAdquisitionPropertyCommand fromRequest(UpdateAdquisitionPropertyRequest request, UUID id) {
        return new UpdateAdquisitionPropertyCommand(
                id,
                request.getBuyer(),
                request.getProperty(),
                request.getContact(),
                request.getBuyerNameAndYearVehicle(),
                request.getBuyerLicenseTagNo(),
                request.getDateAndTimeForInspections(),
                request.getInstructionsForAccess(),
                request.getHoaBuyerInterviewDate(),
                request.getPreferredMoveinDate(),
                request.getESignAuthorization(),
                request.getFinalWalkthroughDate(),
                request.getWireAccountHolderName(),
                request.getWireAccountNumber(),
                request.getWireRoutingNumber(),
                request.getZelleEmailorPhone(),
                request.getElectricProviderConfirmation(),
                request.getGasServiceConfirmation(),
                request.getTrashServiceConfirmation(),
                request.getWaterSewerSetupConfirmation(),
                request.getUploadGovernmentIssuedId(),
                request.getHoaApplicationForm(),
                request.getHoaApplicationUpload(),
                request.getHoaFinancials(),
                request.getHoaRulesRegulations(),
                request.getBuyerCarRegistration(),
                request.getBuyerBackgroundCheck(),
                request.getCommitmentLetter(),
                request.getAppraisalReport(),
                request.getInspectionReport(),
                request.getSellerDisclosureForm(),
                request.getSurveyDocument(),
                request.getTitleCommitment(),
                request.getLegalEntityCertificationStatus(),
                request.getAssignmentOfContract(),
                request.getOwnerExecutedContract(),
                request.getContractAddendum(),
                request.getFinalSettlementStatement(),
                request.getBankStatementRequest(),
                request.getWarrantyDeed(),
                request.getTitleInsurance(),
                request.getExecutedClosingDocuments(),
                request.getBuyerFullLegalName(),
                request.getBuyerContactEmail(),
                request.getBuyerEntityName(),
                request.getBuyerMailingAddress(),
                request.getBuyerMobilePhoneNumber(),
                request.getHoa4050certificationStatus(),
                request.getHoaValidatorContactName(),
                request.getHoaValidatorEmail(),
                request.getHoaValidatorPhoneNumber(),
                request.getClosingCountdownClock(),
                request.getContractClosingDate(),
                request.getSellerFullName(),
                request.getSellerEntityName(),
                request.getSellerArticlesOfIncorporation(),
                request.getSellerCertificateOfGoodStanding(),
                request.getSellerOperatingAgreement(),
                request.getSellerOwnershipType(),
                request.getSellerResolutionToSell(),
                request.getSellerSocialSecurityNumber(),
                request.getSellerMaritalStatus(),
                request.getSellerGovernmentId(),
                request.getSellerW9Form(),
                request.getSellerForeignSeller(),
                request.getSellerFirptaAffidavit(),
                request.getSellerWireAccountHolder(),
                request.getSellerWireAccountNumber(),
                request.getSellerWireRoutingNumber(),
                request.getZelleContact(),
                request.getTitleCompanyRequestForEstoppelLetter(),
                request.getTitleCompanyEarnestMoneyDepositConfirmation(),
                request.getSurveyavailable(),
                request.getRecentImprovementsLast12Months(),
                request.getUploadInvoicesForImprovements(),
                request.getSummarizePropertyCondition(),
                request.getDiscloseKnownRepairsOrDefects(),
                request.getListItemsNotIncludedInSale(),
                request.getIsThereAMortgage(),
                request.getLenderName(),
                request.getLoanNumber(),
                request.getEstimatedPayoffAmount(),
                request.getUploadLatestMortgageStatement(),
                request.getSecondLienOrHeloc(),
                request.getIrsLiensOrJudgments(),
                request.getUploadTaxProrationAgreement(),
                request.getElectricProvider(),
                request.getElectricProviderAccountNumber(),
                request.getWaterSewerProvider(),
                request.getGasProvider(),
                request.getGasProviderAccountNumber(),
                request.getTrashServiceProvider(),
                request.getUploadLatestUtilityBill(),
                request.getUploadSellersDisclosureForm(),
                request.getUploadTenantEstoppel(),
                request.getUploadRentalAgreement(),
                request.getHoaApprovalProcessingTime(),
                request.getHoaDuesAmount(),
                request.getProofOfOwnershipDocument(),
                request.getPowerOfAttorneyDocument(),
                request.getTrustOrEstateDocuments(),
                request.getEnterWireInstructions(),
                request.getAuthorizeTitleCompanyInfo(),
                request.getPropertyAccessCode(),
                request.getTimeForAccess(),
                request.getInstructionsForInspections(),
                request.getHoaQASheet(),
                request.getHoaEstoppelCertificate(),
                request.getHoaApprovalLetter(),
                request.getBuildingYearBuilt(),
                request.getHoaMoveInFee(),
                request.getHoaInterviewRequired(),
                request.getHoaApplicationInstructions(),
                request.getBuyersCarNameAndYear(),
                request.getApplicationFeesAmount(),
                request.getApplicationFeesSentDate(),
                request.getRentalRestrictions(),
                request.getHoaSpecialAssessmentAmount(),
                request.getHoaHaveReserve(),
                request.getHoaCOICertificate(),
                request.getBuyersSocialSecurity(),
                request.getHoaW9TaxID(),
                request.getLienSearch(),
                request.getFinalAgreedSalesPrice(),
                request.getWsalerClosingDate(),
                request.getWsalerAssignmentOfContract(),
                request.getRequestAppraisalDate(),
                request.getConfirmedAppointmentDateTime(),
                request.getAppraisedValue(),
                request.getAppraiserLicenseNumber(),
                request.getAsIsValue(),
                request.getAsRepairedValue(),
                request.getInspectionPhotos(),
                request.getRequiredRepairsNoted(),
                request.getValuationMethod(),
                request.getDateSent(),
                request.getReportSentTo()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateAdquisitionPropertyMessage(id);
    }
}
