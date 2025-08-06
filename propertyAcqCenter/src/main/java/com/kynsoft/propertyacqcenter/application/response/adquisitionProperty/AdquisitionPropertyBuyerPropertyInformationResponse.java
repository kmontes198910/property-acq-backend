package com.kynsoft.propertyacqcenter.application.response.adquisitionProperty;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.application.response.AdquisitionDocumentResponse;
import com.kynsoft.propertyacqcenter.application.response.DocumentMapper;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.AdquisitionPropertyBuyerPropertyInformationDto;
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
public class AdquisitionPropertyBuyerPropertyInformationResponse implements IResponse {

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
    private AdquisitionDocumentResponse lenderSignTermSheet;//File
    private String lenderSignedCreditApplication;
    private AdquisitionDocumentResponse lenderCommitmentLetter;
    private AdquisitionDocumentResponse lenderFinalLoanPackage;//File

    public AdquisitionPropertyBuyerPropertyInformationResponse(AdquisitionPropertyBuyerPropertyInformationDto dto) {
        this.lenderOriginationFee = dto.getLenderOriginationFee();
        this.lenderUnderwritingFee = dto.getLenderUnderwritingFee();
        this.lenderProcessingFee = dto.getLenderProcessingFee();
        this.lenderServicingFee = dto.getLenderServicingFee();
        this.lenderLegalFee = dto.getLenderLegalFee();
        this.lenderAppraisalFee = dto.getLenderAppraisalFee();
        this.lenderAttorneyFees = dto.getLenderAttorneyFees();
        this.lenderLoanType = dto.getLenderLoanType();
        this.lenderLoanAmount = dto.getLenderLoanAmount();
        this.lenderInterestRate = dto.getLenderInterestRate();
        this.lenderLoanTerm = dto.getLenderLoanTerm();
        this.lenderPrepaymentPenalty = dto.getLenderPrepaymentPenalty();
        this.lenderSignTermSheet = DocumentMapper.mapDocumentField(dto.getLenderSignTermSheet());
        this.lenderSignedCreditApplication = dto.getLenderSignedCreditApplication();
        this.lenderCommitmentLetter = DocumentMapper.mapDocumentField(dto.getLenderCommitmentLetter());
        this.lenderFinalLoanPackage = DocumentMapper.mapDocumentField(dto.getLenderFinalLoanPackage());
    }

}
