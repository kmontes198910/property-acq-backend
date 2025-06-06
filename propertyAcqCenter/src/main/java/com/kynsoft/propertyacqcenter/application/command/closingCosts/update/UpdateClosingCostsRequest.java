package com.kynsoft.propertyacqcenter.application.command.closingCosts.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClosingCostsRequest {

    private String property;
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
}
