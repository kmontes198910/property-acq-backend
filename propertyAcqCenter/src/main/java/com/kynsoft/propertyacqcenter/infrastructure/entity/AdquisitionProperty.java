package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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

    @Column(name = "appraisal_report", nullable = true)
    private String appraisalReport;

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

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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

    @Column(name = "hoa_validator_email", nullable = true)
    private String hoaValidatorEmail;

    @Column(name = "hoa_validator_phone_number", nullable = true)
    private String hoaValidatorPhoneNumber;

    @Column(name = "hoa_approval_time", nullable = true)
    private Integer hoaApprovalProcessingTime;

    @Column(name = "hoa_dues_amount", nullable = true)
    private Double hoaDuesAmount;

    @Column(name = "closing_countdown_clock", nullable = true)
    private String closingCountdownClock;

    @Column(name = "contract_closing_date", nullable = true)
    private LocalDate contractClosingDate;

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

    @Column(name = "recent_improvements_last_12_months", nullable = true)
    private String recentImprovementsLast12Months;

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

    public AdquisitionProperty(AdquisitionPropertyDto dto) {
        this.id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        this.buyerNameAndYearVehicle = dto.getBuyerNameAndYearVehicle();
        this.buyerLicenseTagNo = dto.getBuyerLicenseTagNo();
        this.buyer = dto.getBuyer() != null ? new LegalEntity(dto.getBuyer()) : null;
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
        this.contact = dto.getContact() != null ? new CompanyContact(dto.getContact()) : null;

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
    }

    public AdquisitionPropertyDto toAggregate() {
        return AdquisitionPropertyDto.builder()
                .id(this.id)
                .buyer(this.buyer != null ? this.buyer.toAggregateBasic() : null)
                .property(this.property != null ? this.property.toAggregateBasic() : null)
                .contact(this.contact != null ? this.contact.toAggregate() : null)
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
                .build();
    }

    public AdquisitionPropertyDto toAggregateBasic() {
        return AdquisitionPropertyDto.builder()
                .id(this.id)
                .build();
    }
}
