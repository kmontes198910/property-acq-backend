package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClosingCostsDto implements Serializable {

    private UUID id;
    private PropertyDto property;

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