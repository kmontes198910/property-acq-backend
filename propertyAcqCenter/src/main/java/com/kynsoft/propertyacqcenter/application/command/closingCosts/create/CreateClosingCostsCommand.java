package com.kynsoft.propertyacqcenter.application.command.closingCosts.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateClosingCostsCommand implements ICommand {

    private UUID id;
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

    public CreateClosingCostsCommand(String property, Double administrationFee, Double attorneyFee, Double documentPreparation, Double mortgageBrokerFee, Double processing, Double taxService, Double titleSearch, Double applicationFee, Double commitmentFee, Double floodCertification, Double pestInspection, Double recordingFee, Double taxes, Double underwriting, Double appraisal, Double creditReport, Double fundingFee, Double points, Double survey, Double titleInsurance, Double otherFees) {
        this.id = UUID.randomUUID();
        this.property = property;
        this.administrationFee = administrationFee;
        this.attorneyFee = attorneyFee;
        this.documentPreparation = documentPreparation;
        this.mortgageBrokerFee = mortgageBrokerFee;
        this.processing = processing;
        this.taxService = taxService;
        this.titleSearch = titleSearch;
        this.applicationFee = applicationFee;
        this.commitmentFee = commitmentFee;
        this.floodCertification = floodCertification;
        this.pestInspection = pestInspection;
        this.recordingFee = recordingFee;
        this.taxes = taxes;
        this.underwriting = underwriting;
        this.appraisal = appraisal;
        this.creditReport = creditReport;
        this.fundingFee = fundingFee;
        this.points = points;
        this.survey = survey;
        this.titleInsurance = titleInsurance;
        this.otherFees = otherFees;
    }

    public static CreateClosingCostsCommand fromRequest(CreateClosingCostsRequest request) {
        return new CreateClosingCostsCommand(
                request.getProperty(),
                request.getAdministrationFee(),
                request.getAttorneyFee(),
                request.getDocumentPreparation(),
                request.getMortgageBrokerFee(),
                request.getProcessing(),
                request.getTaxService(),
                request.getTitleSearch(),
                request.getApplicationFee(),
                request.getCommitmentFee(),
                request.getFloodCertification(),
                request.getPestInspection(),
                request.getRecordingFee(),
                request.getTaxes(),
                request.getUnderwriting(),
                request.getAppraisal(),
                request.getCreditReport(),
                request.getFundingFee(),
                request.getPoints(),
                request.getSurvey(),
                request.getTitleInsurance(),
                request.getOtherFees()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateClosingCostsMessage(id);
    }
}
