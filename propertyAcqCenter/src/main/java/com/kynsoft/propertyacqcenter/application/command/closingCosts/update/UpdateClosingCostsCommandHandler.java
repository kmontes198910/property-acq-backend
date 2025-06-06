package com.kynsoft.propertyacqcenter.application.command.closingCosts.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.ClosingCostsDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IClosingCostsService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class UpdateClosingCostsCommandHandler implements ICommandHandler<UpdateClosingCostsCommand> {

    private final IClosingCostsService closingCostsService;
    private final IPropertyService propertyService;

    public UpdateClosingCostsCommandHandler(IClosingCostsService closingCostsService, IPropertyService propertyService) {
        this.closingCostsService = closingCostsService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(UpdateClosingCostsCommand command) {
        PropertyDto property = this.propertyService.getById(command.getProperty());

        this.closingCostsService.update(ClosingCostsDto.builder()
                .id(command.getId())
                .property(property)
                .administrationFee(command.getAdministrationFee())
                .attorneyFee(command.getAttorneyFee())
                .documentPreparation(command.getDocumentPreparation())
                .mortgageBrokerFee(command.getMortgageBrokerFee())
                .processing(command.getProcessing())
                .taxService(command.getTaxService())
                .titleSearch(command.getTitleSearch())
                .applicationFee(command.getApplicationFee())
                .commitmentFee(command.getCommitmentFee())
                .floodCertification(command.getFloodCertification())
                .pestInspection(command.getPestInspection())
                .recordingFee(command.getRecordingFee())
                .taxes(command.getTaxes())
                .underwriting(command.getUnderwriting())
                .appraisal(command.getAppraisal())
                .creditReport(command.getCreditReport())
                .fundingFee(command.getFundingFee())
                .points(command.getPoints())
                .survey(command.getSurvey())
                .titleInsurance(command.getTitleInsurance())
                .otherFees(command.getOtherFees())
                .build()
        );
    }

}
