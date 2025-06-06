package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ClosingCostsDto;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClosingCostsResponse implements Serializable, IResponse {

    private UUID id;
    private PropertiesBasicResponse property;

    private Double administrationFee;
    private Double attorneyFee;
    private Double documentPreparation;
    private Double mortgageBrokerFee;
    private Double processing;
    private Double taxService;
    private Double titleSearch;

    private Double applicationFee;
    private Double commitmentFee;
    private Double floodCertification;
    private Double pestInspection;
    private Double recordingFee;
    private Double taxes;
    private Double underwriting;

    private Double appraisal;
    private Double creditReport;
    private Double fundingFee;
    private Double points;
    private Double survey;
    private Double titleInsurance;
    private Double otherFees;

    public ClosingCostsResponse(ClosingCostsDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.administrationFee = dto.getAdministrationFee();
        this.attorneyFee = dto.getAttorneyFee();
        this.documentPreparation = dto.getDocumentPreparation();
        this.mortgageBrokerFee = dto.getMortgageBrokerFee();
        this.processing = dto.getProcessing();
        this.taxService = dto.getTaxService();
        this.titleSearch = dto.getTitleSearch();
        this.applicationFee = dto.getApplicationFee();
        this.commitmentFee = dto.getCommitmentFee();
        this.floodCertification = dto.getFloodCertification();
        this.pestInspection = dto.getPestInspection();
        this.recordingFee = dto.getRecordingFee();
        this.taxes = dto.getTaxes();
        this.underwriting = dto.getUnderwriting();
        this.appraisal = dto.getAppraisal();
        this.creditReport = dto.getCreditReport();
        this.fundingFee = dto.getFundingFee();
        this.points = dto.getPoints();
        this.survey = dto.getSurvey();
        this.titleInsurance = dto.getTitleInsurance();
        this.otherFees = dto.getOtherFees();
    }

    
}