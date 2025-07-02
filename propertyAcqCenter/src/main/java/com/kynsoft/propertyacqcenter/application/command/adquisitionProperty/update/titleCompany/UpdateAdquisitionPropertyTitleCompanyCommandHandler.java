package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.titleCompany;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.UpdateAdquisitionTitleCompanyDto;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import org.springframework.stereotype.Component;

@Component
public class UpdateAdquisitionPropertyTitleCompanyCommandHandler implements ICommandHandler<UpdateAdquisitionPropertyTitleCompanyCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;

    public UpdateAdquisitionPropertyTitleCompanyCommandHandler(IAdquisitionPropertyService adquisitionPropertyService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
    }

    @Override
    public void handle(UpdateAdquisitionPropertyTitleCompanyCommand command) {
        adquisitionPropertyService.updateTitleCompany(UpdateAdquisitionTitleCompanyDto
                .builder()
                .idAdquisition(command.getAdquisitionId())
                .earnestMoneyDepositConfirmation(command.getEarnestMoneyDepositConfirmation() != null ? command.getEarnestMoneyDepositConfirmation().getFilePath() + "|" + command.getEarnestMoneyDepositConfirmation().getFileName() : null)
                .requestForEstoppelLetter(command.getRequestForEstoppelLetter())
                .build()
        );
    }

}
