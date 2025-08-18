package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update;

import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
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
public class AdquisitionPropertyBuyerPropertyInformationRequest {

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
    private CreateDocumentRequest lenderSignTermSheet;//File
    private CreateDocumentRequest lenderSignedCreditApplication;
    private CreateDocumentRequest lenderFinalLoanPackage;//File
    private String lenderAmortizationType;
    private CreateDocumentRequest lenderPayoffInstructions;
    private CreateDocumentRequest lenderHomeownerInsuranceRequirements;
    private CreateDocumentRequest lenderPayoffLetter;//file
    private String lenderPhone;
    private String lenderEmail;
    private String municipalLiens;
    //private CreateDocumentRequest buyerTitleInsurance;//File
    //private CreateDocumentRequest buyerExecutedClosingDocuments;//File
}
