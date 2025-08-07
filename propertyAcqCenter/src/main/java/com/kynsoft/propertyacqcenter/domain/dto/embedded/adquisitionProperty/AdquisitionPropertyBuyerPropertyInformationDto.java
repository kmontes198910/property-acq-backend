package com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyBuyerPropertyInformationDto {

    private Double buyerRepairBudget;
    private String buyerApprovedPlans;//File
    private String buyerPermits;//File
    private Double lenderOriginationFee;
    private Double lenderUnderwritingFee;
    private Double lenderProcessingFee;
    private Double lenderServicingFee;
    private Double lenderLegalFee;
    private Double lenderAppraisalFee;
    private Double lenderAttorneyFees;
    private String lenderLoanType;//(Bridge, DSCR, SBA, Conventional)
    private Double lenderLoanAmount;
    private Double lenderInterestRate;
    private Double lenderLoanTerm;
    private String lenderPrepaymentPenalty;
    private String lenderSignTermSheet;//File
    private String lenderSignedCreditApplication;//File
    private String lenderFinalLoanPackage;//File
    private String buyerTitleInsurance;//File
    private String buyerExecutedClosingDocuments;//File
    private String lenderAmortizationType;
}
