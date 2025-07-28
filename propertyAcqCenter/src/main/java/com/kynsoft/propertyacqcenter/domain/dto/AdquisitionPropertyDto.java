package com.kynsoft.propertyacqcenter.domain.dto;

import java.time.LocalDate;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyDto {

    private UUID id;
    private LegalEntityDto buyer;
    private PropertyDto property;
    private CompanyContactDto contact;
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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    private String uploadGovernmentIssuedId;
    private String buyerCarRegistration;
    private String buyerBackgroundCheck;
    private String commitmentLetter;
    private String appraisalReport;
    private String inspectionReport;
    private String sellerDisclosureForm;
    private String titleCommitment;
    private String legalEntityCertificationStatus;
    private String assignmentOfContract;
    private String ownerExecutedContract;
    private String contractAddendum;
    private String finalSettlementStatement;
    private String bankStatementRequest;
    private String warrantyDeed;
    private String titleInsurance;
    private String executedClosingDocuments;

    private String buyerFullLegalName;
    private String buyerContactEmail;
    private String buyerEntityName;
    private String buyerMailingAddress;
    private String buyerMobilePhoneNumber;
    private String closingCountdownClock;
    private LocalDate contractClosingDate;

    //HOA
    private String hoaApprovalProcessingTime;
    private Double hoaDuesAmount;
    private String hoa4050certificationStatus;
    private String hoaValidatorContactName;
    private String hoaValidatorEmail;
    private String hoaValidatorPhoneNumber;
    private String hoaApplicationForm;
    private String hoaApplicationUpload;
    private String hoaFinancials;
    private String hoaRulesRegulations;

    private String hoaQASheet;
    private String hoaEstoppelCertificate;
    private String hoaApprovalLetter;
    private Integer buildingYearBuilt;
    private Double hoaMoveInFee;
    private Boolean hoaInterviewRequired;
    private String hoaApplicationInstructions;
    private Double applicationFeesAmount;
    private LocalDate applicationFeesSentDate;
    private String rentalRestrictions;
    private Double hoaSpecialAssessmentAmount;
    private String hoaHaveReserve;
    private String hoaCOICertificate;
    private String buyersSocialSecurity;
    private String hoaW9TaxID;

    //Seller
    private String sellerFullName;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private String sellerEntityName;//Sellers, Wholesaler, Real Estate Agent
    private String sellerArticlesOfIncorporation;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private String sellerCertificateOfGoodStanding;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private String sellerOperatingAgreement;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private String sellerOwnershipType;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private String sellerResolutionToSell;

    // Información personal
    private String sellerSocialSecurityNumber;
    private String sellerMaritalStatus;
    private String sellerGovernmentId;
    private String sellerW9Form;

    // Información FIRPTA
    private Boolean sellerForeignSeller;
    private String sellerFirptaAffidavit;

    // Información bancaria
    private String sellerWireAccountHolder;
    private String sellerWireAccountNumber;
    private String sellerWireRoutingNumber;
    private String zelleContact;

    //Title Company
    private LocalDate titleCompanyRequestForEstoppelLetter;
    private String titleCompanyEarnestMoneyDepositConfirmation;

    //Survey & Condition
    private Boolean surveyavailable;
    private String surveyDocument;
    private Boolean recentImprovementsLast12Months;
    private String uploadInvoicesForImprovements;
    private String summarizePropertyCondition;
    private String discloseKnownRepairsOrDefects;
    private String listItemsNotIncludedInSale;

    //Mortgage & Liens
    private Boolean isThereAMortgage;
    private String lenderName;
    private String loanNumber;
    private Double estimatedPayoffAmount;
    private String uploadLatestMortgageStatement;
    private Boolean secondLienOrHeloc;
    private Boolean irsLiensOrJudgments;
    private String uploadTaxProrationAgreement;
    private String lienSearch;
    private Double finalAgreedSalesPrice;

    //Utilities
    private String electricProvider;
    private String electricProviderAccountNumber;
    private String waterSewerProvider;
    private String gasProvider;
    private String gasProviderAccountNumber;
    private String trashServiceProvider;
    private String uploadLatestUtilityBill;

    //Rental
    private String uploadSellersDisclosureForm;
    private String uploadTenantEstoppel;
    private String uploadRentalAgreement;

    //Legal & Estate
    private String proofOfOwnershipDocument;
    private String powerOfAttorneyDocument;
    private String trustOrEstateDocuments;

    //Closing
    private String enterWireInstructions;
    private Boolean authorizeTitleCompanyInfo;

    //Access & Inspection
    private String propertyAccessCode;
    private String timeForAccess;
    private String instructionsForInspections;

    //Wholesaler
    private LocalDate wsalerClosingDate;
    private String wsalerAssignmentOfContract;

    //Appraiser
    private LocalDate requestAppraisalDate;
    private LocalDate confirmedAppointmentDateTime;
    private String appraisedValue;
    private String appraiserLicenseNumber;
    private String asIsValue;
    private String asRepairedValue;
    private String inspectionPhotos;
    private String requiredRepairsNoted;
    private String valuationMethod;
    private LocalDate dateSent;
    private String reportSentTo;

    private String hoaCompanyName;
    private LocalDate hoaInterviewDateProposal;
    private String preferredClosingLocation;
    private String requireElevationCertificate;
    private String elevationCertificate;
    private Boolean outstandingCodeViolations;
    private String taxBillOrAmount;
    private String sellerUploadGovernmentIssuedId;

    private String whOwnershipType;
    private String whwireAccountHolderName;
    private String whwireAccountNumber;
    private String whwireRoutingNumber;
    private String whZelleEmailorPhone;

    private String hoaTotalUnits;
    private String hoaDeclarationOfCondominium;
    private String hoaCondominiumRider;
    private String hoaBylaws;
    private String hoaLatestApprovedBudget;
    private String hoaReserveStudy;
    private String hoaCurrentSpecialAssessmentDisclosure;
    private String hoaPendingLawsuits;
    private String hoaDelinquencyReport;
    private String hoaParkingAssignment;
    private String hoaCondoQuestionnaireForm;
    private String buyerCreditReport;
    private String hoaValidatorWebsite;
    private String hoaApplicationLink;

    //Buyer
    private String buyerProofOfFunds;
    private String buyerCarBrand;
    private Double buyerCarYear;
    private String buyerDriverLicense;
    private String buyerCarInsurance;

    private String buyerPersonalAccountHolderName;
    private String buyerPersonalAccountNumber;
    private String buyerPersonalRoutingNumber;
    private String buyerPersonalZelleEmailorPhone;
    private String buyerPersonalBankStatements;
    private String buyerPersonalBankName;
    private Boolean buyerPersonalUseForHoaBankReference;
    private Boolean buyerPersonalUseForLenderBankReference;
    private Boolean buyerVoidCheck;
    private Boolean buyerLegalEntityUseForHoaBankReference;
    private String buyerBankName;
    private Boolean buyerPersonalVoidCheck;

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
}
