package com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty;

import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.infrastructure.entity.AdquisitionProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "adquisition_property_buyer_property_information")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyBuyerPropertyInformation {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "adquisition_property_id")
    private AdquisitionProperty adquisitionProperty; // Relación bidireccional
    
    @Column(name = "buyer_repair_budget", nullable = true)
    private Double buyerRepairBudget;

    @Column(name = "buyer_approved_plans", nullable = true)
    private String buyerApprovedPlans;//File

    @Column(name = "buyer_permits", nullable = true)
    private String buyerPermits;//File

    @Column(name = "lender_origination_fee", nullable = true)
    private Double lenderOriginationFee;

    @Column(name = "lender_underwriting_fee", nullable = true)
    private Double lenderUnderwritingFee;

    @Column(name = "lender_processing_fee", nullable = true)
    private Double lenderProcessingFee;

    @Column(name = "lender_servicing_fee", nullable = true)
    private Double lenderServicingFee;

    @Column(name = "lender_legal_fee", nullable = true)
    private Double lenderLegalFee;

    @Column(name = "lender_appraisal_fee", nullable = true)
    private Double lenderAppraisalFee;

    @Column(name = "lender_attorney_fees", nullable = true)
    private Double lenderAttorneyFees;

    @Column(name = "lender_loan_type", nullable = true)
    private String lenderLoanType;//(Bridge, DSCR, SBA, Conventional)

    @Column(name = "lender_loan_amount", nullable = true)
    private Double lenderLoanAmount;

    @Column(name = "lender_interest_rate", nullable = true)
    private Double lenderInterestRate;

    @Column(name = "lender_loan_term", nullable = true)
    private Double lenderLoanTerm;

    @Column(name = "lender_prepayment_penalty", nullable = true)
    private String lenderPrepaymentPenalty;

    @Column(name = "lender_sign_term_sheet", nullable = true)
    private String lenderSignTermSheet;//File

    @Column(name = "lender_signed_credit_application", nullable = true)
    private String lenderSignedCreditApplication;

    @Column(name = "lender_commitment_letter", nullable = true)
    private String lenderCommitmentLetter;

    @Column(name = "lender_final_loan_package", nullable = true)
    private String lenderFinalLoanPackage;//File

    @Column(name = "buyer_title_insurance", nullable = true)
    private String buyerTitleInsurance;//File

    @Column(name = "buyer_executed_closing_documents", nullable = true)
    private String buyerExecutedClosingDocuments;//File
}
