package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.ClosingCostsDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "closing_costs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClosingCosts implements Serializable {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

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

    public ClosingCosts(ClosingCostsDto dto) {
        this.id = dto.getId();
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

        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
    }

    public ClosingCostsDto toAggregate() {
        return ClosingCostsDto.builder()
                .id(this.id)
                .administrationFee(administrationFee)
                .attorneyFee(attorneyFee)
                .documentPreparation(documentPreparation)
                .mortgageBrokerFee(mortgageBrokerFee)
                .processing(processing)
                .taxService(taxService)
                .titleSearch(titleSearch)
                .applicationFee(applicationFee)
                .commitmentFee(commitmentFee)
                .floodCertification(floodCertification)
                .pestInspection(pestInspection)
                .recordingFee(recordingFee)
                .taxes(taxes)
                .underwriting(underwriting)
                .appraisal(appraisal)
                .creditReport(creditReport)
                .fundingFee(fundingFee)
                .points(points)
                .survey(survey)
                .titleInsurance(titleInsurance)
                .otherFees(otherFees)
                .property(property.toAggregateBasic())
                .build();
    }
}