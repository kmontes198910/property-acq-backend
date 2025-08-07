package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.AdquisitionPropertyBuyerPropertyInformationDto;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertyBankReference;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertyBuyer;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertyBuyerPersonalBankInfo;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertyBuyerPropertyInformation;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertyBuyerUtilitiesInfo;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertyEmployerReference;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertyHoa;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertyHoaBuildingInfo;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertyPersonalReference;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertySeller;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "adquisition_property")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionProperty {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "legal_entity_id", nullable = true)
    private LegalEntity buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_bank_account_id", nullable = true)
    private BankAccount buyerBankAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_bank_account_id", nullable = true)
    private BankAccount sellerBankAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_contact_id", nullable = true)
    private CompanyContact contact;

    @Column(name = "buyer_name_and_year_vehicle", nullable = true)
    private String buyerNameAndYearVehicle;

    @Column(name = "buyer_license_tag_no", nullable = true)
    private String buyerLicenseTagNo;

    ///
    @Column(name = "date_and_time_for_inspections", nullable = true)
    private LocalDate dateAndTimeForInspections;

    @Column(name = "instructions_for_access", nullable = true)
    private String instructionsForAccess;

    @Column(name = "hoa_buyer_interview_date", nullable = true)
    private LocalDate hoaBuyerInterviewDate;

    @Column(name = "preferred_move_in_date", nullable = true)
    private LocalDate preferredMoveinDate;

    @Column(name = "e_sign_authorization", nullable = true)
    private String eSignAuthorization;

    @Column(name = "final_walkthrough_date", nullable = true)
    private LocalDate finalWalkthroughDate;

    @Column(name = "wire_account_holder_name", nullable = true)
    private String wireAccountHolderName;

    @Column(name = "wire_account_name", nullable = true)
    private String wireAccountNumber;

    @Column(name = "wire_routing_number", nullable = true)
    private String wireRoutingNumber;

    @Column(name = "zelle_email_or_phone", nullable = true)
    private String zelleEmailorPhone;

    @Column(name = "electric_provider_confirmation", nullable = true)
    private String electricProviderConfirmation;

    @Column(name = "gas_service_confirmation", nullable = true)
    private String gasServiceConfirmation;

    @Column(name = "trash_service_confirmation", nullable = true)
    private String trashServiceConfirmation;

    @Column(name = "water_sewer_setup_confirmation", nullable = true)
    private String waterSewerSetupConfirmation;

    //BUYER Document
    @Column(name = "upload_government_issued_id", nullable = true)
    private String uploadGovernmentIssuedId;

    @Column(name = "s_government_issued_id", nullable = true)
    private String sellerUploadGovernmentIssuedId;

    @Column(name = "hoa_application_form", nullable = true)
    private String hoaApplicationForm;

    @Column(name = "hoa_application_upload", nullable = true)
    private String hoaApplicationUpload;

    @Column(name = "hoa_financials", nullable = true)
    private String hoaFinancials;

    @Column(name = "hoa_rules_regulations", nullable = true)
    private String hoaRulesRegulations;

    @Column(name = "buyer_car_registration", nullable = true)
    private String buyerCarRegistration;

    @Column(name = "buyer_background_check", nullable = true)
    private String buyerBackgroundCheck;

    @Column(name = "commitment_letter", nullable = true)
    private String commitmentLetter;

    @Column(name = "inspection_report", nullable = true)
    private String inspectionReport;

    @Column(name = "seller_disclosure_form", nullable = true)
    private String sellerDisclosureForm;

    @Column(name = "survey_document", nullable = true)
    private String surveyDocument;

    @Column(name = "buyer_title_commitment", nullable = true)
    private String titleCommitment;

    @Column(name = "legal_entity_certification_status", nullable = true)
    private String legalEntityCertificationStatus;

    @Column(name = "assignment_of_contract", nullable = true)
    private String assignmentOfContract;

    @Column(name = "owner_executed_contract", nullable = true)
    private String ownerExecutedContract;

    @Column(name = "contract_addendum", nullable = true)
    private String contractAddendum;

    @Column(name = "final_settlement_statement", nullable = true)
    private String finalSettlementStatement;

    @Column(name = "bank_statement_request", nullable = true)
    private String bankStatementRequest;

    @Column(name = "warranty_deed", nullable = true)
    private String warrantyDeed;

    @Column(name = "title_insurance", nullable = true)
    private String titleInsurance;

    @Column(name = "executed_closing_documents", nullable = true)
    private String executedClosingDocuments;
    ///

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "buyer_full_legal_name", nullable = true)
    private String buyerFullLegalName;

    @Column(name = "buyer_contact_email", nullable = true)
    private String buyerContactEmail;

    @Column(name = "buyer_entity_name", nullable = true)
    private String buyerEntityName;

    @Column(name = "buyer_mailing_address", nullable = true)
    private String buyerMailingAddress;

    @Column(name = "buyer_mobile_phone_number", nullable = true)
    private String buyerMobilePhoneNumber;

    @Column(name = "certification_status", nullable = true)
    private String hoa4050certificationStatus;

    @Column(name = "hoa_validator_contact_name", nullable = true)
    private String hoaValidatorContactName;

    @Column(name = "hoa_company_name", nullable = true)
    private String hoaCompanyName;

    @Column(name = "hoa_validator_email", nullable = true)
    private String hoaValidatorEmail;

    @Column(name = "hoa_validator_phone_number", nullable = true)
    private String hoaValidatorPhoneNumber;

    @Column(name = "approval_time", nullable = true)
    private String hoaApprovalProcessingTime;

    @Column(name = "hoa_dues_amount", nullable = true)
    private Double hoaDuesAmount;

    @Column(name = "closing_countdown_clock", nullable = true)
    private String closingCountdownClock;

    @Column(name = "contract_closing_date", nullable = true)
    private LocalDate contractClosingDate;

    @Column(name = "original_contract_closing_date", nullable = true)
    private LocalDate originalContractClosingDate;

    //Seller
    @Column(name = "full_name", nullable = true)
    private String sellerFullName;//Sellers, Wholesaler, Real Estate Agent, Title Co.

    @Column(name = "entity_name", nullable = true)
    private String sellerEntityName;//Sellers, Wholesaler, Real Estate Agent

    @Column(name = "articles_of_incorporation", nullable = true)
    private String sellerArticlesOfIncorporation;//Sellers, Wholesaler, Real Estate Agent, Title Co.

    @Column(name = "certificate_of_good_standing", nullable = true)
    private String sellerCertificateOfGoodStanding;//Sellers, Wholesaler, Real Estate Agent, Title Co.

    @Column(name = "operating_agreement", nullable = true)
    private String sellerOperatingAgreement;//Sellers, Wholesaler, Real Estate Agent, Title Co.

    @Column(name = "type_of_ownership", nullable = true)
    private String sellerOwnershipType;//Sellers, Wholesaler, Real Estate Agent, Title Co.

    @Column(name = "resolution_to_sell", nullable = true)
    private String sellerResolutionToSell;

    // Información personal
    @Column(name = "social_security_number", nullable = true)
    private String sellerSocialSecurityNumber;

    @Column(name = "marital_status", nullable = true)
    private String sellerMaritalStatus;

    @Column(name = "government_id", nullable = true)
    private String sellerGovernmentId;

    @Column(name = "w9_form", nullable = true)
    private String sellerW9Form;

    // Información FIRPTA
    @Column(name = "is_foreign_seller", nullable = true)
    private Boolean sellerForeignSeller;

    @Column(name = "firpta_affidavit", nullable = true)
    private String sellerFirptaAffidavit;

    // Información bancaria
    @Column(name = "seller_wire_account_holder", nullable = true)
    private String sellerWireAccountHolder;

    @Column(name = "seller_wire_account_number", nullable = true)
    private String sellerWireAccountNumber;

    @Column(name = "seller_wire_routing_number", nullable = true)
    private String sellerWireRoutingNumber;

    @Column(name = "zelle_contact", nullable = true)
    private String zelleContact;

    //Title Company
    @Column(name = "request_for_estoppel_letter")
    private LocalDate titleCompanyRequestForEstoppelLetter;

    @Column(name = "earnest_money_deposit_confirmation", nullable = true)
    private String titleCompanyEarnestMoneyDepositConfirmation;

    //Survey & Condition
    @Column(name = "surveya_vailable", nullable = true)
    private Boolean surveyavailable;

    @Column(name = "recent_improvements_12mo", nullable = true)
    private Boolean recentImprovementsLast12Months;

    @Column(name = "upload_invoices_for_improvements", nullable = true)
    private String uploadInvoicesForImprovements;

    @Column(name = "summarize_property_condition", nullable = true)
    private String summarizePropertyCondition;

    @Column(name = "disclose_known_repairs_or_defects", nullable = true)
    private String discloseKnownRepairsOrDefects;

    @Column(name = "list_items_not_included_in_sale", nullable = true)
    private String listItemsNotIncludedInSale;

    //Mortgage & Liens
    @Column(name = "is_there_a_mortgage", nullable = true)
    private Boolean isThereAMortgage;

    @Column(name = "lender_name", nullable = true)
    private String lenderName;

    @Column(name = "loan_number", nullable = true)
    private String loanNumber;

    @Column(name = "estimated_payoff_amount", nullable = true)
    private Double estimatedPayoffAmount;

    @Column(name = "upload_latest_mortgage_statement", nullable = true)
    private String uploadLatestMortgageStatement;

    @Column(name = "second_lien_or_heloc", nullable = true)
    private Boolean secondLienOrHeloc;

    @Column(name = "irs_liens_or_judgments", nullable = true)
    private Boolean irsLiensOrJudgments;

    @Column(name = "upload_tax_proration_agreement", nullable = true)
    private String uploadTaxProrationAgreement;

    @Column(name = "lien_search", nullable = true)
    private String lienSearch;

    @Column(name = "final_agreed_sales_price", nullable = true)
    private Double finalAgreedSalesPrice;

    //Utilities
    @Column(name = "electric_provider", nullable = true)
    private String electricProvider;

    @Column(name = "electric_provider_account_number", nullable = true)
    private String electricProviderAccountNumber;

    @Column(name = "water_sewer_provider", nullable = true)
    private String waterSewerProvider;

    @Column(name = "gas_provider", nullable = true)
    private String gasProvider;

    @Column(name = "gas_provider_account_number", nullable = true)
    private String gasProviderAccountNumber;

    @Column(name = "trash_service_provider", nullable = true)
    private String trashServiceProvider;

    @Column(name = "upload_latest_utility_bill", nullable = true)
    private String uploadLatestUtilityBill;

    //Rental
    @Column(name = "upload_sellers_disclosure_form", nullable = true)
    private String uploadSellersDisclosureForm;

    @Column(name = "upload_tenant_estoppel", nullable = true)
    private String uploadTenantEstoppel;

    @Column(name = "upload_rental_agreement", nullable = true)
    private String uploadRentalAgreement;

    //Legal & Estate
    @Column(name = "proof_of_ownership_deed", nullable = true)
    private String proofOfOwnershipDocument;

    @Column(name = "power_of_attorney_upload", nullable = true)
    private String powerOfAttorneyDocument;

    @Column(name = "trust_estate_docs_upload", nullable = true)
    private String trustOrEstateDocuments;

    //Closing
    @Column(name = "enter_wire_instructions", nullable = true)
    private String enterWireInstructions;

    @Column(name = "authorize_tc_info", nullable = true)
    private Boolean authorizeTitleCompanyInfo;

    //Access & Inspection
    @Column(name = "property_access_code", nullable = true)
    private String propertyAccessCode;

    @Column(name = "time_for_access", nullable = true)
    private String timeForAccess;

    @Column(name = "instructions_for_inspections", nullable = true)
    private String instructionsForInspections;

    //HOA
    @Column(name = "hoa_qa_sheet", nullable = true)
    private String hoaQASheet;

    @Column(name = "hoa_estoppel_certificate", nullable = true)
    private String hoaEstoppelCertificate;

    @Column(name = "hoa_approval_letter", nullable = true)
    private String hoaApprovalLetter;

    @Column(name = "building_year_built", nullable = true)
    private Integer buildingYearBuilt;

    @Column(name = "hoa_move_in_fee", nullable = true)
    private Double hoaMoveInFee;

    @Column(name = "hoa_interview_required", nullable = true)
    private Boolean hoaInterviewRequired;

    @Column(name = "hoa_interview_date_proposal", nullable = true)
    private LocalDate hoaInterviewDateProposal;

    @Column(name = "preferred_closing_location", nullable = true)
    private String preferredClosingLocation;

    @Column(name = "require_elevation_certificate", nullable = true)
    private String requireElevationCertificate;

    @Column(name = "elevation_certificate", nullable = true)
    private String elevationCertificate;

    @Column(name = "hoa_application_instructions", nullable = true)
    private String hoaApplicationInstructions;

    @Column(name = "application_fees_amount", nullable = true)
    private Double applicationFeesAmount;

    @Column(name = "application_fees_sent_date", nullable = true)
    private LocalDate applicationFeesSentDate;

    @Column(name = "rental_restrictions", nullable = true)
    private String rentalRestrictions;

    @Column(name = "hoa_special_assessment_amount", nullable = true)
    private Double hoaSpecialAssessmentAmount;

    @Column(name = "hoa_have_reserve", nullable = true)
    private String hoaHaveReserve;

    @Column(name = "hoa_coi_certificate", nullable = true)
    private String hoaCOICertificate;

    @Column(name = "buyers_social_security", nullable = true)
    private String buyersSocialSecurity;

    @Column(name = "hoa_w9_tax_id", nullable = true)
    private String hoaW9TaxID;

    //Wholesaler
    @Column(name = "wsaler_closing_date", nullable = true)
    private LocalDate wsalerClosingDate;

    @Column(name = "wsaler_assignment_of_contract", nullable = true)
    private String wsalerAssignmentOfContract;

    //Appraiser
    @Column(name = "request_appraisal_date", nullable = true)
    private LocalDate requestAppraisalDate;

    @Column(name = "confirmed_appointment_date_time", nullable = true)
    private LocalDate confirmedAppointmentDateTime;

    @Column(name = "appraisal_report", nullable = true)
    private String appraisalReport;

    @Column(name = "appraised_value", nullable = true)
    private String appraisedValue;

    @Column(name = "appraiser_license_number", nullable = true)
    private String appraiserLicenseNumber;

    @Column(name = "as_is_value", nullable = true)
    private String asIsValue;

    @Column(name = "as_repaired_value", nullable = true)
    private String asRepairedValue;

    @Column(name = "inspection_photos", nullable = true)
    private String inspectionPhotos;

    @Column(name = "required_repairs_noted", nullable = true)
    private String requiredRepairsNoted;

    @Column(name = "valuation_method", nullable = true)
    private String valuationMethod;

    @Column(name = "date_sent", nullable = true)
    private LocalDate dateSent;

    @Column(name = "report_sent_to", nullable = true)
    private String reportSentTo;

    @Column(name = "outstanding_code_violation", nullable = true)
    private Boolean outstandingCodeViolations;

    @Column(name = "tax_bill_amount", nullable = true)
    private String taxBillOrAmount;

    @Column(name = "wh_type_of_ownership", nullable = true)
    private String whOwnershipType;

    @Column(name = "wh_wire_account_holder_name", nullable = true)
    private String whwireAccountHolderName;

    @Column(name = "wh_wire_account_name", nullable = true)
    private String whwireAccountNumber;

    @Column(name = "wh_wire_routing_number", nullable = true)
    private String whwireRoutingNumber;

    @Column(name = "wh_zelle_email_or_phone", nullable = true)
    private String whZelleEmailorPhone;

    @Column(name = "execute_hud", nullable = true)
    private String executeHud;

    @OneToOne(mappedBy = "adquisitionProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AdquisitionPropertyHoa adquisitionPropertyHoa;

    @OneToOne(mappedBy = "adquisitionProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AdquisitionPropertyHoaBuildingInfo adquisitionPropertyHoaBuildingInfo;

    @OneToOne(mappedBy = "adquisitionProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AdquisitionPropertyBuyer adquisitionPropertyBuyer;

    @OneToOne(mappedBy = "adquisitionProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AdquisitionPropertyBuyerPersonalBankInfo adquisitionPropertyBuyerPersonalBankInfo;

    @OneToOne(mappedBy = "adquisitionProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AdquisitionPropertyBuyerUtilitiesInfo adquisitionPropertyBuyerUtilitiesInfo;

    @OneToOne(mappedBy = "adquisitionProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AdquisitionPropertySeller adquisitionPropertySeller;

    @OneToOne(mappedBy = "adquisitionProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AdquisitionPropertyEmployerReference adquisitionPropertyEmployerReference;

    @OneToOne(mappedBy = "adquisitionProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AdquisitionPropertyBankReference adquisitionPropertyBankReference;

    @OneToOne(mappedBy = "adquisitionProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AdquisitionPropertyPersonalReference adquisitionPropertyPersonalReference;

    @OneToOne(mappedBy = "adquisitionProperty", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AdquisitionPropertyBuyerPropertyInformation adquisitionPropertyBuyerPropertyInformation;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public AdquisitionProperty(AdquisitionPropertyDto dto) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.buyerNameAndYearVehicle = dto.getBuyerNameAndYearVehicle();
        this.buyerLicenseTagNo = dto.getBuyerLicenseTagNo();
        this.buyer = dto.getBuyer() != null ? new LegalEntity(dto.getBuyer()) : null;
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
        this.contact = dto.getContact() != null ? new CompanyContact(dto.getContact()) : null;

        this.buyerBankAccount = dto.getBuyerBankAccount() != null ? new BankAccount(dto.getBuyerBankAccount()) : null;
        this.sellerBankAccount = dto.getBuyerBankAccount() != null ? new BankAccount(dto.getBuyerBankAccount()) : null;

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

        this.sellerFullName = dto.getSellerFullName();
        this.sellerEntityName = dto.getSellerEntityName();
        this.sellerArticlesOfIncorporation = dto.getSellerArticlesOfIncorporation();
        this.sellerCertificateOfGoodStanding = dto.getSellerCertificateOfGoodStanding();
        this.sellerOperatingAgreement = dto.getSellerOperatingAgreement();
        this.sellerOwnershipType = dto.getSellerOwnershipType();
        this.sellerResolutionToSell = dto.getSellerResolutionToSell();
        this.sellerSocialSecurityNumber = dto.getSellerSocialSecurityNumber();
        this.sellerMaritalStatus = dto.getSellerMaritalStatus();
        this.sellerGovernmentId = dto.getSellerGovernmentId();
        this.sellerW9Form = dto.getSellerW9Form();
        this.sellerForeignSeller = dto.getSellerForeignSeller();
        this.sellerFirptaAffidavit = dto.getSellerFirptaAffidavit();

        this.sellerWireAccountHolder = dto.getSellerWireAccountHolder();
        this.sellerWireAccountNumber = dto.getSellerWireAccountNumber();
        this.sellerWireRoutingNumber = dto.getSellerWireRoutingNumber();
        this.zelleContact = dto.getZelleContact();

        this.titleCompanyRequestForEstoppelLetter = dto.getTitleCompanyRequestForEstoppelLetter();
        this.titleCompanyEarnestMoneyDepositConfirmation = dto.getTitleCompanyEarnestMoneyDepositConfirmation();

        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();

        this.uploadGovernmentIssuedId = dto.getUploadGovernmentIssuedId();
        this.hoaApplicationForm = dto.getHoaApplicationForm();
        this.hoaApplicationUpload = dto.getHoaApplicationUpload();
        this.hoaFinancials = dto.getHoaFinancials();
        this.hoaRulesRegulations = dto.getHoaRulesRegulations();
        this.buyerCarRegistration = dto.getBuyerCarRegistration();
        this.buyerBackgroundCheck = dto.getBuyerBackgroundCheck();
        this.commitmentLetter = dto.getCommitmentLetter();
        this.appraisalReport = dto.getAppraisalReport();
        this.inspectionReport = dto.getInspectionReport();
        this.sellerDisclosureForm = dto.getSellerDisclosureForm();
        this.surveyDocument = dto.getSurveyDocument();
        this.titleCommitment = dto.getTitleCommitment();
        this.legalEntityCertificationStatus = dto.getLegalEntityCertificationStatus();
        this.assignmentOfContract = dto.getAssignmentOfContract();
        this.ownerExecutedContract = dto.getOwnerExecutedContract();
        this.contractAddendum = dto.getContractAddendum();
        this.finalSettlementStatement = dto.getFinalSettlementStatement();
        this.bankStatementRequest = dto.getBankStatementRequest();
        this.warrantyDeed = dto.getWarrantyDeed();
        this.titleInsurance = dto.getTitleInsurance();
        this.executedClosingDocuments = dto.getExecutedClosingDocuments();

        this.buyerFullLegalName = dto.getBuyerFullLegalName();
        this.buyerContactEmail = dto.getBuyerContactEmail();
        this.buyerEntityName = dto.getBuyerEntityName();
        this.buyerMailingAddress = dto.getBuyerMailingAddress();
        this.buyerMobilePhoneNumber = dto.getBuyerMobilePhoneNumber();
        this.hoa4050certificationStatus = dto.getHoa4050certificationStatus();
        this.hoaValidatorContactName = dto.getHoaValidatorContactName();
        this.hoaValidatorEmail = dto.getHoaValidatorEmail();
        this.hoaValidatorPhoneNumber = dto.getHoaValidatorPhoneNumber();
        this.closingCountdownClock = dto.getClosingCountdownClock();
        this.contractClosingDate = dto.getContractClosingDate();

        this.surveyavailable = dto.getSurveyavailable();
        this.recentImprovementsLast12Months = dto.getRecentImprovementsLast12Months();
        this.uploadInvoicesForImprovements = dto.getUploadInvoicesForImprovements();
        this.summarizePropertyCondition = dto.getSummarizePropertyCondition();
        this.discloseKnownRepairsOrDefects = dto.getDiscloseKnownRepairsOrDefects();
        this.listItemsNotIncludedInSale = dto.getListItemsNotIncludedInSale();

        this.isThereAMortgage = dto.getIsThereAMortgage();
        this.lenderName = dto.getLenderName();
        this.loanNumber = dto.getLoanNumber();
        this.estimatedPayoffAmount = dto.getEstimatedPayoffAmount();
        this.uploadLatestMortgageStatement = dto.getUploadLatestMortgageStatement();
        this.secondLienOrHeloc = dto.getSecondLienOrHeloc();
        this.irsLiensOrJudgments = dto.getIrsLiensOrJudgments();
        this.uploadTaxProrationAgreement = dto.getUploadTaxProrationAgreement();
        this.lienSearch = dto.getLienSearch();
        this.finalAgreedSalesPrice = dto.getFinalAgreedSalesPrice();

        this.electricProvider = dto.getElectricProvider();
        this.electricProviderAccountNumber = dto.getElectricProviderAccountNumber();
        this.waterSewerProvider = dto.getWaterSewerProvider();
        this.gasProvider = dto.getGasProvider();
        this.gasProviderAccountNumber = dto.getGasProviderAccountNumber();
        this.trashServiceProvider = dto.getTrashServiceProvider();
        this.uploadLatestUtilityBill = dto.getUploadLatestUtilityBill();

        this.uploadSellersDisclosureForm = dto.getUploadSellersDisclosureForm();
        this.uploadTenantEstoppel = dto.getUploadTenantEstoppel();
        this.uploadRentalAgreement = dto.getUploadRentalAgreement();

        this.hoaApprovalProcessingTime = dto.getHoaApprovalProcessingTime();
        this.hoaDuesAmount = dto.getHoaDuesAmount();

        this.proofOfOwnershipDocument = dto.getProofOfOwnershipDocument();
        this.powerOfAttorneyDocument = dto.getPowerOfAttorneyDocument();
        this.trustOrEstateDocuments = dto.getTrustOrEstateDocuments();

        this.enterWireInstructions = dto.getEnterWireInstructions();
        this.authorizeTitleCompanyInfo = dto.getAuthorizeTitleCompanyInfo();

        this.propertyAccessCode = dto.getPropertyAccessCode();
        this.timeForAccess = dto.getTimeForAccess();
        this.instructionsForInspections = dto.getInstructionsForInspections();

        this.hoaQASheet = dto.getHoaQASheet();
        this.hoaEstoppelCertificate = dto.getHoaEstoppelCertificate();
        this.hoaApprovalLetter = dto.getHoaApprovalLetter();
        this.buildingYearBuilt = dto.getBuildingYearBuilt();
        this.hoaMoveInFee = dto.getHoaMoveInFee();
        this.hoaInterviewRequired = dto.getHoaInterviewRequired();
        this.hoaApplicationInstructions = dto.getHoaApplicationInstructions();
        this.applicationFeesAmount = dto.getApplicationFeesAmount();
        this.applicationFeesSentDate = dto.getApplicationFeesSentDate();
        this.rentalRestrictions = dto.getRentalRestrictions();
        this.hoaSpecialAssessmentAmount = dto.getHoaSpecialAssessmentAmount();
        this.hoaHaveReserve = dto.getHoaHaveReserve();
        this.hoaCOICertificate = dto.getHoaCOICertificate();
        this.buyersSocialSecurity = dto.getBuyersSocialSecurity();
        this.hoaW9TaxID = dto.getHoaW9TaxID();

        this.wsalerClosingDate = dto.getWsalerClosingDate();
        this.wsalerAssignmentOfContract = dto.getWsalerAssignmentOfContract();

        this.requestAppraisalDate = dto.getRequestAppraisalDate();
        this.confirmedAppointmentDateTime = dto.getConfirmedAppointmentDateTime();
        this.appraisedValue = dto.getAppraisedValue();
        this.appraiserLicenseNumber = dto.getAppraiserLicenseNumber();
        this.asIsValue = dto.getAsIsValue();
        this.asRepairedValue = dto.getAsRepairedValue();
        this.inspectionPhotos = dto.getInspectionPhotos();
        this.requiredRepairsNoted = dto.getRequiredRepairsNoted();
        this.valuationMethod = dto.getValuationMethod();
        this.dateSent = dto.getDateSent();
        this.reportSentTo = dto.getReportSentTo();

        this.hoaCompanyName = dto.getHoaCompanyName();
        this.hoaInterviewDateProposal = dto.getHoaInterviewDateProposal();
        this.preferredClosingLocation = dto.getPreferredClosingLocation();
        this.requireElevationCertificate = dto.getRequireElevationCertificate();
        this.elevationCertificate = dto.getElevationCertificate();
        this.outstandingCodeViolations = dto.getOutstandingCodeViolations();
        this.taxBillOrAmount = dto.getTaxBillOrAmount();
        this.sellerUploadGovernmentIssuedId = dto.getSellerUploadGovernmentIssuedId();

        this.whOwnershipType = dto.getWhOwnershipType();
        this.whwireAccountHolderName = dto.getWhwireAccountHolderName();
        this.whwireAccountNumber = dto.getWhwireAccountNumber();
        this.whwireRoutingNumber = dto.getWhwireRoutingNumber();
        this.whZelleEmailorPhone = dto.getWhZelleEmailorPhone();
    }

    public AdquisitionPropertyDto toAggregate() {
        return AdquisitionPropertyDto.builder()
                .id(this.id)
                .buyer(this.buyer != null ? this.buyer.toAggregateBasic() : null)
                .property(this.property != null ? this.property.toAggregateBasic() : null)
                .contact(this.contact != null ? this.contact.toAggregate() : null)
                .buyerBankAccount(this.buyerBankAccount != null ? this.buyerBankAccount.toAggregateToAdquisition() : null)
                .sellerBankAccount(this.sellerBankAccount != null ? this.sellerBankAccount.toAggregateToAdquisition() : null)
                .buyerNameAndYearVehicle(buyerNameAndYearVehicle)
                .buyerLicenseTagNo(buyerLicenseTagNo)
                .dateAndTimeForInspections(dateAndTimeForInspections)
                .instructionsForAccess(instructionsForAccess)
                .hoaBuyerInterviewDate(hoaBuyerInterviewDate)
                .preferredMoveinDate(preferredMoveinDate)
                .eSignAuthorization(eSignAuthorization)
                .finalWalkthroughDate(finalWalkthroughDate)
                .wireAccountHolderName(wireAccountHolderName)
                .wireAccountNumber(wireAccountNumber)
                .wireRoutingNumber(wireRoutingNumber)
                .zelleEmailorPhone(zelleEmailorPhone)
                .electricProviderConfirmation(electricProviderConfirmation)
                .gasServiceConfirmation(gasServiceConfirmation)
                .trashServiceConfirmation(trashServiceConfirmation)
                .waterSewerSetupConfirmation(waterSewerSetupConfirmation)
                .uploadGovernmentIssuedId(uploadGovernmentIssuedId)
                .hoaApplicationForm(hoaApplicationForm)
                .hoaApplicationUpload(hoaApplicationUpload)
                .hoaFinancials(hoaFinancials)
                .hoaRulesRegulations(hoaRulesRegulations)
                .buyerCarRegistration(buyerCarRegistration)
                .buyerBackgroundCheck(buyerBackgroundCheck)
                .commitmentLetter(commitmentLetter)
                .appraisalReport(appraisalReport)
                .inspectionReport(inspectionReport)
                .sellerDisclosureForm(sellerDisclosureForm)
                .surveyDocument(surveyDocument)
                .titleCommitment(titleCommitment)
                .legalEntityCertificationStatus(legalEntityCertificationStatus)
                .assignmentOfContract(assignmentOfContract)
                .ownerExecutedContract(ownerExecutedContract)
                .contractAddendum(contractAddendum)
                .finalSettlementStatement(finalSettlementStatement)
                .bankStatementRequest(bankStatementRequest)
                .warrantyDeed(warrantyDeed)
                .titleInsurance(titleInsurance)
                .executedClosingDocuments(executedClosingDocuments)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .buyerFullLegalName(buyerFullLegalName)
                .buyerContactEmail(buyerContactEmail)
                .buyerEntityName(buyerEntityName)
                .buyerMailingAddress(buyerMailingAddress)
                .buyerMobilePhoneNumber(buyerMobilePhoneNumber)
                .hoa4050certificationStatus(hoa4050certificationStatus)
                .hoaValidatorContactName(hoaValidatorContactName)
                .hoaValidatorEmail(hoaValidatorEmail)
                .hoaValidatorPhoneNumber(hoaValidatorPhoneNumber)
                .closingCountdownClock(closingCountdownClock)
                .contractClosingDate(contractClosingDate)
                .sellerFullName(sellerFullName)
                .sellerEntityName(sellerEntityName)
                .sellerArticlesOfIncorporation(sellerArticlesOfIncorporation)
                .sellerCertificateOfGoodStanding(sellerCertificateOfGoodStanding)
                .sellerOperatingAgreement(sellerOperatingAgreement)
                .sellerOwnershipType(sellerOwnershipType)
                .sellerResolutionToSell(sellerResolutionToSell)
                .sellerSocialSecurityNumber(sellerSocialSecurityNumber)
                .sellerMaritalStatus(sellerMaritalStatus)
                .sellerGovernmentId(sellerGovernmentId)
                .sellerW9Form(sellerW9Form)
                .sellerForeignSeller(sellerForeignSeller)
                .sellerFirptaAffidavit(sellerFirptaAffidavit)
                .sellerWireAccountHolder(sellerWireAccountHolder)
                .sellerWireAccountNumber(sellerWireAccountNumber)
                .sellerWireRoutingNumber(sellerWireRoutingNumber)
                .zelleContact(zelleContact)
                .titleCompanyRequestForEstoppelLetter(titleCompanyRequestForEstoppelLetter)
                .titleCompanyEarnestMoneyDepositConfirmation(titleCompanyEarnestMoneyDepositConfirmation)
                .surveyavailable(surveyavailable)
                .recentImprovementsLast12Months(recentImprovementsLast12Months)
                .uploadInvoicesForImprovements(uploadInvoicesForImprovements)
                .summarizePropertyCondition(summarizePropertyCondition)
                .discloseKnownRepairsOrDefects(discloseKnownRepairsOrDefects)
                .listItemsNotIncludedInSale(listItemsNotIncludedInSale)
                .isThereAMortgage(isThereAMortgage)
                .lenderName(lenderName)
                .loanNumber(loanNumber)
                .estimatedPayoffAmount(estimatedPayoffAmount)
                .uploadLatestMortgageStatement(uploadLatestMortgageStatement)
                .secondLienOrHeloc(secondLienOrHeloc)
                .irsLiensOrJudgments(irsLiensOrJudgments)
                .uploadTaxProrationAgreement(uploadTaxProrationAgreement)
                .electricProvider(electricProvider)
                .electricProviderAccountNumber(electricProviderAccountNumber)
                .waterSewerProvider(waterSewerProvider)
                .gasProvider(gasProvider)
                .gasProviderAccountNumber(gasProviderAccountNumber)
                .trashServiceProvider(trashServiceProvider)
                .uploadLatestUtilityBill(uploadLatestUtilityBill)
                .uploadSellersDisclosureForm(uploadSellersDisclosureForm)
                .uploadTenantEstoppel(uploadTenantEstoppel)
                .uploadRentalAgreement(uploadRentalAgreement)
                .hoaApprovalProcessingTime(hoaApprovalProcessingTime)
                .hoaDuesAmount(hoaDuesAmount)
                .proofOfOwnershipDocument(proofOfOwnershipDocument)
                .powerOfAttorneyDocument(powerOfAttorneyDocument)
                .trustOrEstateDocuments(trustOrEstateDocuments)
                .enterWireInstructions(enterWireInstructions)
                .authorizeTitleCompanyInfo(authorizeTitleCompanyInfo)
                .propertyAccessCode(propertyAccessCode)
                .timeForAccess(timeForAccess)
                .instructionsForInspections(instructionsForInspections)
                .hoaQASheet(hoaQASheet)
                .hoaEstoppelCertificate(hoaEstoppelCertificate)
                .hoaApprovalLetter(hoaApprovalLetter)
                .buildingYearBuilt(buildingYearBuilt)
                .hoaMoveInFee(hoaMoveInFee)
                .hoaInterviewRequired(hoaInterviewRequired)
                .hoaApplicationInstructions(hoaApplicationInstructions)
                .applicationFeesAmount(applicationFeesAmount)
                .applicationFeesSentDate(applicationFeesSentDate)
                .rentalRestrictions(rentalRestrictions)
                .hoaSpecialAssessmentAmount(hoaSpecialAssessmentAmount)
                .hoaHaveReserve(hoaHaveReserve)
                .hoaCOICertificate(hoaCOICertificate)
                .buyersSocialSecurity(buyersSocialSecurity)
                .hoaW9TaxID(hoaW9TaxID)
                .lienSearch(lienSearch)
                .finalAgreedSalesPrice(finalAgreedSalesPrice)
                .wsalerClosingDate(wsalerClosingDate)
                .wsalerAssignmentOfContract(wsalerAssignmentOfContract)
                .requestAppraisalDate(requestAppraisalDate)
                .confirmedAppointmentDateTime(confirmedAppointmentDateTime)
                .appraisedValue(appraisedValue)
                .appraiserLicenseNumber(appraiserLicenseNumber)
                .asIsValue(asIsValue)
                .asRepairedValue(asRepairedValue)
                .inspectionPhotos(inspectionPhotos)
                .requiredRepairsNoted(requiredRepairsNoted)
                .valuationMethod(valuationMethod)
                .dateSent(dateSent)
                .reportSentTo(reportSentTo)
                .hoaCompanyName(hoaCompanyName)
                .hoaInterviewDateProposal(hoaInterviewDateProposal)
                .preferredClosingLocation(preferredClosingLocation)
                .requireElevationCertificate(requireElevationCertificate)
                .elevationCertificate(elevationCertificate)
                .outstandingCodeViolations(outstandingCodeViolations)
                .taxBillOrAmount(taxBillOrAmount)
                .sellerUploadGovernmentIssuedId(sellerUploadGovernmentIssuedId)
                .whOwnershipType(whOwnershipType)
                .whwireAccountHolderName(whwireAccountHolderName)
                .whwireAccountNumber(whwireAccountNumber)
                .whwireRoutingNumber(whwireRoutingNumber)
                .whZelleEmailorPhone(whZelleEmailorPhone)
                //AdquisitionPropertyHoa
                .hoaTotalUnits(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaTotalUnits() : null)
                .hoaDeclarationOfCondominium(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaDeclarationOfCondominium() : null)
                .hoaCondominiumRider(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaCondominiumRider() : null)
                .hoaBylaws(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaBylaws() : null)
                .hoaLatestApprovedBudget(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaLatestApprovedBudget() : null)
                .hoaReserveStudy(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaReserveStudy() : null)
                .hoaCurrentSpecialAssessmentDisclosure(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaCurrentSpecialAssessmentDisclosure() : null)
                .hoaPendingLawsuits(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaPendingLawsuits() : null)
                .hoaDelinquencyReport(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaDelinquencyReport() : null)
                .hoaParkingAssignment(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaParkingAssignment() : null)
                .hoaCondoQuestionnaireForm(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaCondoQuestionnaireForm() : null)
                .buyerCreditReport(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getBuyerCreditReport() : null)
                .hoaValidatorWebsite(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaValidatorWebsite() : null)
                .hoaApplicationLink(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaApplicationLink() : null)
                //AdquisitionPropertyBuyer
                .buyerProofOfFunds(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerProofOfFunds() : null)
                .buyerCarBrand(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerCarBrand() : null)
                .buyerCarYear(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerCarYear() : null)
                .buyerDriverLicense(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerDriverLicense() : null)
                .buyerCarInsurance(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerCarInsurance() : null)
                .buyerBankName(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerBankName() : null)
                .buyerPersonalVoidCheck(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerPersonalVoidCheck() : null)
                .buyerMaritalStatus(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerMaritalStatus() : null)
                //AdquisitionPropertyBuyerPersonalBankInfo
                .buyerPersonalAccountHolderName(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalAccountHolderName() : null)
                .buyerPersonalAccountNumber(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalAccountNumber() : null)
                .buyerPersonalRoutingNumber(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalRoutingNumber() : null)
                .buyerPersonalZelleEmailorPhone(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalZelleEmailorPhone() : null)
                .buyerPersonalBankStatements(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalBankStatements() : null)
                .buyerPersonalBankName(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalBankName() : null)
                .buyerPersonalUseForHoaBankReference(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalUseForHoaBankReference() : null)
                .buyerPersonalUseForLenderBankReference(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalUseForLenderBankReference() : null)
                .buyerVoidCheck(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerVoidCheck() : null)
                .buyerLegalEntityUseForHoaBankReference(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerLegalEntityUseForHoaBankReference() : null)
                //AdquisitionPropertyBuyerUtilitiesInfo
                .buyerElectricProviderAccount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerElectricProviderAccount() : null)
                .buyerGasServiceAccount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerGasServiceAccount() : null)
                .buyerTrashServiceAccount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerTrashServiceAccount() : null)
                .buyerWaterSewerSetupAccount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerWaterSewerSetupAccount() : null)
                .buyerInternetService(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerInternetService() : null)
                .buyerNotes(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerNotes() : null)
                .buyerStartServiceDate(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerStartServiceDate() : null)
                .buyerDepositAmount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerDepositAmount() : null)
                .gasBuyerStartServiceDate(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getGasBuyerStartServiceDate() : null)
                .gasBuyerDepositAmount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getGasBuyerDepositAmount() : null)
                .trashBuyerStartServiceDate(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getTrashBuyerStartServiceDate() : null)
                .trashBuyerDepositAmount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getTrashBuyerDepositAmount() : null)
                .waterBuyerStartServiceDate(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getWaterBuyerStartServiceDate() : null)
                .waterBuyerDepositAmount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getWaterBuyerDepositAmount() : null)
                .internetBuyerStartServiceDate(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getInternetBuyerStartServiceDate() : null)
                .internetBuyerDepositAmount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getInternetBuyerDepositAmount() : null)
                .sellerPersonalAccountHolderName(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerPersonalAccountHolderName() : null)
                .sellerPersonalAccountNumber(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerPersonalAccountNumber() : null)
                .sellerPersonalRoutingNumber(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerPersonalRoutingNumber() : null)
                .sellerPersonalZelleEmailorPhone(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerPersonalZelleEmailorPhone() : null)
                .sellerPersonalBankName(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerPersonalBankName() : null)
                .sellerBankStatementRequest(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerBankStatementRequest() : null)
                .sellerPersonalBankStatements(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerPersonalBankStatements() : null)
                .sellerVoidCheck(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerVoidCheck() : null)
                .sellerPersonalVoidCheck(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerPersonalVoidCheck() : null)
                .hoaInpectionReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaInpectionReport() : null)
                .hoaElectricalReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaElectricalReport() : null)
                .hoaHvacReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaHvacReport() : null)
                .hoaRoofReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaRoofReport() : null)
                .hoaStructuralReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaStructuralReport() : null)
                .hoaPlumbingReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaPlumbingReport() : null)
                .hoaNotesReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaNotesReport() : null)
                .hoaOthersReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaOthersReport() : null)
                .hoaNotes(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaNotes() : null)
                .bankReferenceName(adquisitionPropertyBankReference != null ? adquisitionPropertyBankReference.getBankReferenceName() : null)
                .bankReferencePhone(adquisitionPropertyBankReference != null ? adquisitionPropertyBankReference.getBankReferencePhone() : null)
                .bankReferenceEmail(adquisitionPropertyBankReference != null ? adquisitionPropertyBankReference.getBankReferenceEmail() : null)
                .bankReferencePosition(adquisitionPropertyBankReference != null ? adquisitionPropertyBankReference.getBankReferencePosition() : null)
                .personalReferenceName(adquisitionPropertyPersonalReference != null ? adquisitionPropertyPersonalReference.getPersonalReferenceName() : null)
                .personalReferencePhone(adquisitionPropertyPersonalReference != null ? adquisitionPropertyPersonalReference.getPersonalReferencePhone() : null)
                .personalReferenceEmail(adquisitionPropertyPersonalReference != null ? adquisitionPropertyPersonalReference.getPersonalReferenceEmail() : null)
                .personalReferencePosition(adquisitionPropertyPersonalReference != null ? adquisitionPropertyPersonalReference.getPersonalReferencePosition() : null)
                .employerReferenceName(adquisitionPropertyEmployerReference != null ? adquisitionPropertyEmployerReference.getEmployerReferenceName() : null)
                .employerReferencePhone(adquisitionPropertyEmployerReference != null ? adquisitionPropertyEmployerReference.getEmployerReferencePhone() : null)
                .employerReferenceEmail(adquisitionPropertyEmployerReference != null ? adquisitionPropertyEmployerReference.getEmployerReferenceEmail() : null)
                .employerReferencePosition(adquisitionPropertyEmployerReference != null ? adquisitionPropertyEmployerReference.getEmployerReferencePosition() : null)
                .originalContractClosingDate(originalContractClosingDate)
                .executeHud(executeHud)
                .buyerRepairBudget(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerRepairBudget() : null)
                .buyerApprovedPlans(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerApprovedPlans() : null)
                .buyerPermits(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerPermits() : null)
                .lender(AdquisitionPropertyBuyerPropertyInformationDto
                        .builder()
                        .buyerRepairBudget(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerRepairBudget() : null)
                        .buyerApprovedPlans(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerApprovedPlans() : null)
                        .buyerPermits(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerPermits() : null)
                        .lenderOriginationFee(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderOriginationFee() : null)
                        .lenderUnderwritingFee(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderUnderwritingFee() : null)
                        .lenderProcessingFee(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderProcessingFee() : null)
                        .lenderServicingFee(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderServicingFee() : null)
                        .lenderLegalFee(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderLegalFee() : null)
                        .lenderAppraisalFee(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderAppraisalFee() : null)
                        .lenderAttorneyFees(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderAttorneyFees() : null)
                        .lenderLoanType(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderLoanType() : null)
                        .lenderLoanAmount(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderLoanAmount() : null)
                        .lenderInterestRate(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderInterestRate() : null)
                        .lenderLoanTerm(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderLoanTerm() : null)
                        .lenderPrepaymentPenalty(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderPrepaymentPenalty() : null)
                        .lenderSignTermSheet(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderSignTermSheet() : null)
                        .lenderSignedCreditApplication(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderSignedCreditApplication() : null)
                        .lenderFinalLoanPackage(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderFinalLoanPackage() : null)
                        .buyerTitleInsurance(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerTitleInsurance() : null)
                        .lenderAmortizationType(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderAmortizationType() : null)
                        .buyerExecutedClosingDocuments(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerExecutedClosingDocuments() : null)
                        .lenderPayoffInstructions(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderPayoffInstructions() : null)
                        .lenderHomeownerInsuranceRequirements(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderHomeownerInsuranceRequirements() : null)
                        .build())
                .build();
    }

    public AdquisitionPropertyDocumentDto toAggregateByPropertyId() {
        return AdquisitionPropertyDocumentDto.builder()
                .id(this.id)
                .buyer(this.buyer != null ? this.buyer.toAggregateBasic() : null)
                .property(this.property != null ? this.property.toAggregateBasic() : null)
                .contact(this.contact != null ? this.contact.toAggregate() : null)
                .buyerBankAccount(this.buyerBankAccount != null ? this.buyerBankAccount.toAggregateToAdquisition() : null)
                .sellerBankAccount(this.sellerBankAccount != null ? this.sellerBankAccount.toAggregateToAdquisition() : null)
                .buyerNameAndYearVehicle(buyerNameAndYearVehicle)
                .buyerLicenseTagNo(buyerLicenseTagNo)
                .dateAndTimeForInspections(dateAndTimeForInspections)
                .instructionsForAccess(instructionsForAccess)
                .hoaBuyerInterviewDate(hoaBuyerInterviewDate)
                .preferredMoveinDate(preferredMoveinDate)
                .eSignAuthorization(eSignAuthorization)
                .finalWalkthroughDate(finalWalkthroughDate)
                .wireAccountHolderName(wireAccountHolderName)
                .wireAccountNumber(wireAccountNumber)
                .wireRoutingNumber(wireRoutingNumber)
                .zelleEmailorPhone(zelleEmailorPhone)
                .electricProviderConfirmation(electricProviderConfirmation)
                .gasServiceConfirmation(gasServiceConfirmation)
                .trashServiceConfirmation(trashServiceConfirmation)
                .waterSewerSetupConfirmation(waterSewerSetupConfirmation)
                .uploadGovernmentIssuedId(uploadGovernmentIssuedId)
                .hoaApplicationForm(hoaApplicationForm)
                .hoaApplicationUpload(hoaApplicationUpload)
                .hoaFinancials(hoaFinancials)
                .hoaRulesRegulations(hoaRulesRegulations)
                .buyerCarRegistration(buyerCarRegistration)
                .buyerBackgroundCheck(buyerBackgroundCheck)
                .commitmentLetter(commitmentLetter)
                .appraisalReport(appraisalReport)
                .inspectionReport(inspectionReport)
                .sellerDisclosureForm(sellerDisclosureForm)
                .surveyDocument(surveyDocument)
                .titleCommitment(titleCommitment)
                .legalEntityCertificationStatus(legalEntityCertificationStatus)
                .assignmentOfContract(assignmentOfContract)
                .ownerExecutedContract(ownerExecutedContract)
                .contractAddendum(contractAddendum)
                .finalSettlementStatement(finalSettlementStatement)
                .bankStatementRequest(this.convertDbToList(bankStatementRequest))
                .warrantyDeed(warrantyDeed)
                .titleInsurance(titleInsurance)
                .executedClosingDocuments(executedClosingDocuments)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .buyerFullLegalName(buyerFullLegalName)
                .buyerContactEmail(buyerContactEmail)
                .buyerEntityName(buyerEntityName)
                .buyerMailingAddress(buyerMailingAddress)
                .buyerMobilePhoneNumber(buyerMobilePhoneNumber)
                .hoa4050certificationStatus(hoa4050certificationStatus)
                .hoaValidatorContactName(hoaValidatorContactName)
                .hoaValidatorEmail(hoaValidatorEmail)
                .hoaValidatorPhoneNumber(hoaValidatorPhoneNumber)
                .closingCountdownClock(closingCountdownClock)
                .contractClosingDate(contractClosingDate)
                .sellerFullName(sellerFullName)
                .sellerEntityName(sellerEntityName)
                .sellerArticlesOfIncorporation(sellerArticlesOfIncorporation)
                .sellerCertificateOfGoodStanding(sellerCertificateOfGoodStanding)
                .sellerOperatingAgreement(sellerOperatingAgreement)
                .sellerOwnershipType(sellerOwnershipType)
                .sellerResolutionToSell(sellerResolutionToSell)
                .sellerSocialSecurityNumber(sellerSocialSecurityNumber)
                .sellerMaritalStatus(sellerMaritalStatus)
                .sellerGovernmentId(sellerGovernmentId)
                .sellerW9Form(sellerW9Form)
                .sellerForeignSeller(sellerForeignSeller)
                .sellerFirptaAffidavit(sellerFirptaAffidavit)
                .sellerWireAccountHolder(sellerWireAccountHolder)
                .sellerWireAccountNumber(sellerWireAccountNumber)
                .sellerWireRoutingNumber(sellerWireRoutingNumber)
                .zelleContact(zelleContact)
                .titleCompanyRequestForEstoppelLetter(titleCompanyRequestForEstoppelLetter)
                .titleCompanyEarnestMoneyDepositConfirmation(titleCompanyEarnestMoneyDepositConfirmation)
                .surveyavailable(surveyavailable)
                .recentImprovementsLast12Months(recentImprovementsLast12Months)
                .uploadInvoicesForImprovements(uploadInvoicesForImprovements)
                .summarizePropertyCondition(summarizePropertyCondition)
                .discloseKnownRepairsOrDefects(discloseKnownRepairsOrDefects)
                .listItemsNotIncludedInSale(listItemsNotIncludedInSale)
                .isThereAMortgage(isThereAMortgage)
                .lenderName(lenderName)
                .loanNumber(loanNumber)
                .estimatedPayoffAmount(estimatedPayoffAmount)
                .uploadLatestMortgageStatement(uploadLatestMortgageStatement)
                .secondLienOrHeloc(secondLienOrHeloc)
                .irsLiensOrJudgments(irsLiensOrJudgments)
                .uploadTaxProrationAgreement(uploadTaxProrationAgreement)
                .electricProvider(electricProvider)
                .electricProviderAccountNumber(electricProviderAccountNumber)
                .waterSewerProvider(waterSewerProvider)
                .gasProvider(gasProvider)
                .gasProviderAccountNumber(gasProviderAccountNumber)
                .trashServiceProvider(trashServiceProvider)
                .uploadLatestUtilityBill(uploadLatestUtilityBill)
                .uploadSellersDisclosureForm(uploadSellersDisclosureForm)
                .uploadTenantEstoppel(uploadTenantEstoppel)
                .uploadRentalAgreement(uploadRentalAgreement)
                .hoaApprovalProcessingTime(hoaApprovalProcessingTime)
                .hoaDuesAmount(hoaDuesAmount)
                .proofOfOwnershipDocument(proofOfOwnershipDocument)
                .powerOfAttorneyDocument(powerOfAttorneyDocument)
                .trustOrEstateDocuments(trustOrEstateDocuments)
                .enterWireInstructions(enterWireInstructions)
                .authorizeTitleCompanyInfo(authorizeTitleCompanyInfo)
                .propertyAccessCode(propertyAccessCode)
                .timeForAccess(timeForAccess)
                .instructionsForInspections(instructionsForInspections)
                .hoaQASheet(hoaQASheet)
                .hoaEstoppelCertificate(hoaEstoppelCertificate)
                .hoaApprovalLetter(hoaApprovalLetter)
                .buildingYearBuilt(buildingYearBuilt)
                .hoaMoveInFee(hoaMoveInFee)
                .hoaInterviewRequired(hoaInterviewRequired)
                .hoaApplicationInstructions(hoaApplicationInstructions)
                .applicationFeesAmount(applicationFeesAmount)
                .applicationFeesSentDate(applicationFeesSentDate)
                .rentalRestrictions(rentalRestrictions)
                .hoaSpecialAssessmentAmount(hoaSpecialAssessmentAmount)
                .hoaHaveReserve(hoaHaveReserve)
                .hoaCOICertificate(hoaCOICertificate)
                .buyersSocialSecurity(buyersSocialSecurity)
                .hoaW9TaxID(hoaW9TaxID)
                .lienSearch(lienSearch)
                .finalAgreedSalesPrice(finalAgreedSalesPrice)
                .wsalerClosingDate(wsalerClosingDate)
                .wsalerAssignmentOfContract(wsalerAssignmentOfContract)
                .requestAppraisalDate(requestAppraisalDate)
                .confirmedAppointmentDateTime(confirmedAppointmentDateTime)
                .appraisedValue(appraisedValue)
                .appraiserLicenseNumber(appraiserLicenseNumber)
                .asIsValue(asIsValue)
                .asRepairedValue(asRepairedValue)
                .inspectionPhotos(inspectionPhotos)
                .requiredRepairsNoted(requiredRepairsNoted)
                .valuationMethod(valuationMethod)
                .dateSent(dateSent)
                .reportSentTo(reportSentTo)
                .hoaCompanyName(hoaCompanyName)
                .hoaInterviewDateProposal(hoaInterviewDateProposal)
                .preferredClosingLocation(preferredClosingLocation)
                .requireElevationCertificate(requireElevationCertificate)
                .elevationCertificate(elevationCertificate)
                .outstandingCodeViolations(outstandingCodeViolations)
                .taxBillOrAmount(taxBillOrAmount)
                .sellerUploadGovernmentIssuedId(sellerUploadGovernmentIssuedId)
                .whOwnershipType(whOwnershipType)
                .whwireAccountHolderName(whwireAccountHolderName)
                .whwireAccountNumber(whwireAccountNumber)
                .whwireRoutingNumber(whwireRoutingNumber)
                .whZelleEmailorPhone(whZelleEmailorPhone)
                //AdquisitionPropertyHoa
                .hoaTotalUnits(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaTotalUnits() : null)
                .hoaDeclarationOfCondominium(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaDeclarationOfCondominium() : null)
                .hoaCondominiumRider(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaCondominiumRider() : null)
                .hoaBylaws(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaBylaws() : null)
                .hoaLatestApprovedBudget(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaLatestApprovedBudget() : null)
                .hoaReserveStudy(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaReserveStudy() : null)
                .hoaCurrentSpecialAssessmentDisclosure(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaCurrentSpecialAssessmentDisclosure() : null)
                .hoaPendingLawsuits(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaPendingLawsuits() : null)
                .hoaDelinquencyReport(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaDelinquencyReport() : null)
                .hoaParkingAssignment(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaParkingAssignment() : null)
                .hoaCondoQuestionnaireForm(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaCondoQuestionnaireForm() : null)
                .buyerCreditReport(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getBuyerCreditReport() : null)
                .hoaValidatorWebsite(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaValidatorWebsite() : null)
                .hoaApplicationLink(adquisitionPropertyHoa != null ? adquisitionPropertyHoa.getHoaApplicationLink() : null)
                //AdquisitionPropertyBuyer
                .buyerProofOfFunds(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerProofOfFunds() : null)
                .buyerCarBrand(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerCarBrand() : null)
                .buyerCarYear(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerCarYear() : null)
                .buyerDriverLicense(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerDriverLicense() : null)
                .buyerCarInsurance(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerCarInsurance() : null)
                .buyerBankName(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerBankName() : null)
                .buyerPersonalVoidCheck(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerPersonalVoidCheck() : null)
                .buyerMaritalStatus(adquisitionPropertyBuyer != null ? adquisitionPropertyBuyer.getBuyerMaritalStatus() : null)
                //AdquisitionPropertyBuyerPersonalBankInfo
                .buyerPersonalAccountHolderName(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalAccountHolderName() : null)
                .buyerPersonalAccountNumber(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalAccountNumber() : null)
                .buyerPersonalRoutingNumber(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalRoutingNumber() : null)
                .buyerPersonalZelleEmailorPhone(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalZelleEmailorPhone() : null)
                .buyerPersonalBankStatements(adquisitionPropertyBuyerPersonalBankInfo != null ? this.convertDbToList(adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalBankStatements()) : null)
                .buyerPersonalBankName(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalBankName() : null)
                .buyerPersonalUseForHoaBankReference(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalUseForHoaBankReference() : null)
                .buyerPersonalUseForLenderBankReference(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerPersonalUseForLenderBankReference() : null)
                .buyerVoidCheck(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerVoidCheck() : null)
                .buyerLegalEntityUseForHoaBankReference(adquisitionPropertyBuyerPersonalBankInfo != null ? adquisitionPropertyBuyerPersonalBankInfo.getBuyerLegalEntityUseForHoaBankReference() : null)
                //AdquisitionPropertyBuyerUtilitiesInfo
                .buyerElectricProviderAccount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerElectricProviderAccount() : null)
                .buyerGasServiceAccount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerGasServiceAccount() : null)
                .buyerTrashServiceAccount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerTrashServiceAccount() : null)
                .buyerWaterSewerSetupAccount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerWaterSewerSetupAccount() : null)
                .buyerInternetService(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerInternetService() : null)
                .buyerNotes(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerNotes() : null)
                .buyerStartServiceDate(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerStartServiceDate() : null)
                .buyerDepositAmount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getBuyerDepositAmount() : null)
                .gasBuyerStartServiceDate(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getGasBuyerStartServiceDate() : null)
                .gasBuyerDepositAmount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getGasBuyerDepositAmount() : null)
                .trashBuyerStartServiceDate(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getTrashBuyerStartServiceDate() : null)
                .trashBuyerDepositAmount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getTrashBuyerDepositAmount() : null)
                .waterBuyerStartServiceDate(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getWaterBuyerStartServiceDate() : null)
                .waterBuyerDepositAmount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getWaterBuyerDepositAmount() : null)
                .internetBuyerStartServiceDate(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getInternetBuyerStartServiceDate() : null)
                .internetBuyerDepositAmount(adquisitionPropertyBuyerUtilitiesInfo != null ? adquisitionPropertyBuyerUtilitiesInfo.getInternetBuyerDepositAmount() : null)
                .sellerPersonalAccountHolderName(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerPersonalAccountHolderName() : null)
                .sellerPersonalAccountNumber(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerPersonalAccountNumber() : null)
                .sellerPersonalRoutingNumber(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerPersonalRoutingNumber() : null)
                .sellerPersonalZelleEmailorPhone(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerPersonalZelleEmailorPhone() : null)
                .sellerPersonalBankName(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerPersonalBankName() : null)
                .sellerBankStatementRequest(adquisitionPropertySeller != null ? this.convertDbToList(adquisitionPropertySeller.getSellerBankStatementRequest()) : null)
                .sellerPersonalBankStatements(adquisitionPropertySeller != null ? this.convertDbToList(adquisitionPropertySeller.getSellerPersonalBankStatements()) : null)
                .sellerVoidCheck(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerVoidCheck() : null)
                .sellerPersonalVoidCheck(adquisitionPropertySeller != null ? adquisitionPropertySeller.getSellerPersonalVoidCheck() : null)
                .hoaInpectionReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaInpectionReport() : null)
                .hoaElectricalReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaElectricalReport() : null)
                .hoaHvacReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaHvacReport() : null)
                .hoaRoofReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaRoofReport() : null)
                .hoaStructuralReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaStructuralReport() : null)
                .hoaPlumbingReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaPlumbingReport() : null)
                .hoaNotesReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaNotesReport() : null)
                .hoaOthersReport(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaOthersReport() : null)
                .hoaNotes(adquisitionPropertyHoaBuildingInfo != null ? adquisitionPropertyHoaBuildingInfo.getHoaNotes() : null)
                .employerReferenceName(adquisitionPropertyBankReference != null ? adquisitionPropertyBankReference.getBankReferenceName() : null)
                .bankReferencePhone(adquisitionPropertyBankReference != null ? adquisitionPropertyBankReference.getBankReferencePhone() : null)
                .bankReferenceEmail(adquisitionPropertyBankReference != null ? adquisitionPropertyBankReference.getBankReferenceEmail() : null)
                .bankReferencePosition(adquisitionPropertyBankReference != null ? adquisitionPropertyBankReference.getBankReferencePosition() : null)
                .personalReferenceName(adquisitionPropertyPersonalReference != null ? adquisitionPropertyPersonalReference.getPersonalReferenceName() : null)
                .personalReferencePhone(adquisitionPropertyPersonalReference != null ? adquisitionPropertyPersonalReference.getPersonalReferencePhone() : null)
                .personalReferenceEmail(adquisitionPropertyPersonalReference != null ? adquisitionPropertyPersonalReference.getPersonalReferenceEmail() : null)
                .personalReferencePosition(adquisitionPropertyPersonalReference != null ? adquisitionPropertyPersonalReference.getPersonalReferencePosition() : null)
                .employerReferenceName(adquisitionPropertyEmployerReference != null ? adquisitionPropertyEmployerReference.getEmployerReferenceName() : null)
                .employerReferencePhone(adquisitionPropertyEmployerReference != null ? adquisitionPropertyEmployerReference.getEmployerReferencePhone() : null)
                .employerReferenceEmail(adquisitionPropertyEmployerReference != null ? adquisitionPropertyEmployerReference.getEmployerReferenceEmail() : null)
                .employerReferencePosition(adquisitionPropertyEmployerReference != null ? adquisitionPropertyEmployerReference.getEmployerReferencePosition() : null)
                .originalContractClosingDate(originalContractClosingDate)
                .executeHud(executeHud)
                .buyerRepairBudget(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerRepairBudget() : null)
                .buyerApprovedPlans(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerApprovedPlans() : null)
                .buyerPermits(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerPermits() : null)
                .lender(AdquisitionPropertyBuyerPropertyInformationDto
                        .builder()
                        .buyerRepairBudget(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerRepairBudget() : null)
                        .buyerApprovedPlans(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerApprovedPlans() : null)
                        .buyerPermits(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerPermits() : null)
                        .lenderOriginationFee(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderOriginationFee() : null)
                        .lenderUnderwritingFee(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderUnderwritingFee() : null)
                        .lenderProcessingFee(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderProcessingFee() : null)
                        .lenderServicingFee(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderServicingFee() : null)
                        .lenderLegalFee(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderLegalFee() : null)
                        .lenderAppraisalFee(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderAppraisalFee() : null)
                        .lenderAttorneyFees(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderAttorneyFees() : null)
                        .lenderLoanType(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderLoanType() : null)
                        .lenderLoanAmount(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderLoanAmount() : null)
                        .lenderInterestRate(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderInterestRate() : null)
                        .lenderLoanTerm(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderLoanTerm() : null)
                        .lenderPrepaymentPenalty(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderPrepaymentPenalty() : null)
                        .lenderSignTermSheet(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderSignTermSheet() : null)
                        .lenderSignedCreditApplication(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderSignedCreditApplication() : null)
                        .lenderFinalLoanPackage(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderFinalLoanPackage() : null)
                        .buyerTitleInsurance(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerTitleInsurance() : null)
                        .buyerExecutedClosingDocuments(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getBuyerExecutedClosingDocuments() : null)
                        .lenderAmortizationType(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderAmortizationType() : null)
                        .lenderPayoffInstructions(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderPayoffInstructions() : null)
                        .lenderHomeownerInsuranceRequirements(adquisitionPropertyBuyerPropertyInformation != null ? adquisitionPropertyBuyerPropertyInformation.getLenderHomeownerInsuranceRequirements() : null)
                        .build())
                .build();
    }

    public AdquisitionPropertyDto toAggregateBasic() {
        return AdquisitionPropertyDto.builder()
                .id(this.id)
                .build();
    }

    private List<AdquisitionDocumentDto> convertDbToList(String dbData) {
        List<AdquisitionDocumentDto> result = new ArrayList<>();
        if (dbData == null || dbData.isEmpty()) {
            return result;
        }

        String[] parts = dbData.split("\\|");
        // Asumimos que los datos vienen en pares fileName, filePath
        for (int i = 0; i < parts.length; i += 2) {
            if (i + 1 < parts.length) {
                AdquisitionDocumentDto request = new AdquisitionDocumentDto();
                request.setFileName(parts[i]);
                request.setFilePath(parts[i + 1]);
                result.add(request);
            }
        }
        return result;
    }
}
