package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.AdquisitionTitleCompanyDto;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionTitleCompany;
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

    @Embedded
    private AdquisitionTitleCompany titleCompany;

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

        this.titleCompany = dto.getTitleCompany() != null ? new AdquisitionTitleCompany(dto.getTitleCompany()) : null;

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
                .build();
    }

    public AdquisitionPropertyDto toAggregateTitleCompany() {
        return AdquisitionPropertyDto.builder()
                .id(this.id)
                .property(this.property != null ? this.property.toAggregateBasic() : null)
                .titleCompany(AdquisitionTitleCompanyDto
                        .builder()
                        .titleCommitment(titleCompany.getTitleCommitment())
                        .build())
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .build();
    }

    public AdquisitionPropertyDto toAggregateBasic() {
        return AdquisitionPropertyDto.builder()
                .id(this.id)
                .build();
    }
}
