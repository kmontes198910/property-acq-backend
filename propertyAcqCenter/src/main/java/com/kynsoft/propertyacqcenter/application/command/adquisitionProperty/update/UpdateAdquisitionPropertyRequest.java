package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update;

import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAdquisitionPropertyRequest {

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
    private CreateDocumentRequest legalEntityCertificationStatus;
    private CreateDocumentRequest assignmentOfContract;
    private CreateDocumentRequest ownerExecutedContract;
    private CreateDocumentRequest contractAddendum;
    private CreateDocumentRequest executedClosingDocuments;

    //TODO: estos campos son optional, no necesarios en el momento actual, solo agregamos por si acaso.
    private String buyerFullLegalName;
    private String buyerContactEmail;
    private String buyerEntityName;
    private String buyerMailingAddress;
    private String buyerMobilePhoneNumber;
    private CreateDocumentRequest hoa4050certificationStatus;
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
    private CreateDocumentRequest titleCommitment;
    private CreateDocumentRequest finalSettlementStatement;
    //private CreateDocumentRequest bankStatementRequest;
    private CreateDocumentRequest titleInsurance;
    private CreateDocumentRequest warrantyDeed;

    //Survey & Condition
    private Boolean surveyavailable;
    private CreateDocumentRequest surveyDocument;
    private Boolean recentImprovementsLast12Months;
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

    //Legal & Estate
    private CreateDocumentRequest proofOfOwnershipDocument;
    private CreateDocumentRequest powerOfAttorneyDocument;
    private CreateDocumentRequest trustOrEstateDocuments;

    //Closing
    private String enterWireInstructions;
    private Boolean authorizeTitleCompanyInfo;

    //Access & Inspection
    private String propertyAccessCode;
    private String timeForAccess;
    private String instructionsForInspections;

    //HOA
    private String hoaApprovalProcessingTime;
    private Double hoaDuesAmount;

    private CreateDocumentRequest hoaQASheet;
    private CreateDocumentRequest hoaEstoppelCertificate;
    private CreateDocumentRequest hoaApprovalLetter;
    private Integer buildingYearBuilt;
    private Double hoaMoveInFee;
    private Boolean hoaInterviewRequired;
    private CreateDocumentRequest hoaApplicationInstructions;
    private Double applicationFeesAmount;
    private LocalDate applicationFeesSentDate;
    private String rentalRestrictions;
    private Double hoaSpecialAssessmentAmount;
    private CreateDocumentRequest hoaHaveReserve;
    private CreateDocumentRequest hoaCOICertificate;
    private String buyersSocialSecurity;
    private CreateDocumentRequest hoaW9TaxID;

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

    private String hoaCompanyName;
    private LocalDate hoaInterviewDateProposal;
    private String preferredClosingLocation;
    private CreateDocumentRequest requireElevationCertificate;
    private CreateDocumentRequest elevationCertificate;
    private Boolean outstandingCodeViolations;
    private CreateDocumentRequest taxBillOrAmount;
    private CreateDocumentRequest sellerUploadGovernmentIssuedId;

    private String whOwnershipType;
    private String whwireAccountHolderName;
    private String whwireAccountNumber;
    private String whwireRoutingNumber;
    private String whZelleEmailorPhone;

    private Double hoaTotalUnits;
    private CreateDocumentRequest hoaDeclarationOfCondominium;
    private CreateDocumentRequest hoaCondominiumRider;
    private CreateDocumentRequest hoaBylaws;
    private CreateDocumentRequest hoaLatestApprovedBudget;
    private CreateDocumentRequest hoaReserveStudy;
    private CreateDocumentRequest hoaCurrentSpecialAssessmentDisclosure;
    private CreateDocumentRequest hoaPendingLawsuits;
    private CreateDocumentRequest hoaDelinquencyReport;
    private CreateDocumentRequest hoaParkingAssignment;
    private CreateDocumentRequest hoaCondoQuestionnaireForm;
    private CreateDocumentRequest buyerCreditReport;
    private String hoaValidatorWebsite;
    private String hoaApplicationLink;

    private CreateDocumentRequest buyerProofOfFunds;
    private String buyerCarBrand;
    private Double buyerCarYear;
    private CreateDocumentRequest buyerDriverLicense;
    private CreateDocumentRequest buyerCarInsurance;

    private String buyerPersonalAccountHolderName;
    private String buyerPersonalAccountNumber;
    private String buyerPersonalRoutingNumber;
    private String buyerPersonalZelleEmailorPhone;
    //private CreateDocumentRequest buyerPersonalBankStatements;
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
    private String sellerBankStatementRequest;//array
    private String sellerPersonalBankStatements;//array
    private Boolean sellerVoidCheck;
    private Boolean sellerPersonalVoidCheck;

    private UUID buyerBankAccount;
    private UUID sellerBankAccount;

    private CreateDocumentRequest hoaInpectionReport;
    private CreateDocumentRequest hoaElectricalReport;
    private CreateDocumentRequest hoaHvacReport;
    private CreateDocumentRequest hoaRoofReport;
    private CreateDocumentRequest hoaStructuralReport;
    private CreateDocumentRequest hoaPlumbingReport;
    private String hoaNotesReport;
    private CreateDocumentRequest hoaOthersReport;
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

    private CreateDocumentRequest executeHud;
    private Double buyerRepairBudget;
    private CreateDocumentRequest buyerApprovedPlans;//File
    private CreateDocumentRequest buyerPermits;//File
    private AdquisitionPropertyBuyerPropertyInformationRequest lender;
}
