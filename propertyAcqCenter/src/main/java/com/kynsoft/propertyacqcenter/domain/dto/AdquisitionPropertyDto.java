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
    private String hoaApplicationForm;
    private String hoaApplicationUpload;
    private String hoaFinancials;
    private String hoaRulesRegulations;
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
    private String hoa4050certificationStatus;
    private String hoaValidatorContactName;
    private String hoaValidatorEmail;
    private String hoaValidatorPhoneNumber;
    private String closingCountdownClock;
    private LocalDate contractClosingDate;

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
    private String recentImprovementsLast12Months;
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

    //Utilities
    private String electricProvider;
    private String electricProviderAccountNumber;
    private String waterSewerProvider;
    private String gasProvider;
    private String gasProviderAccountNumber;
    private String trashServiceProvider;
    private String uploadLatestUtilityBill;
}