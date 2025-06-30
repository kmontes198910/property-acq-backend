package com.kynsoft.propertyacqcenter.application.command.propertyTeam.updateAll;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdatePropertyTeamAllCommandHandler implements ICommandHandler<UpdatePropertyTeamAllCommand> {

    private final IPropertyService propertyService;
    private final ILegalEntityService legalEntityService;

    public UpdatePropertyTeamAllCommandHandler(IPropertyService propertyService,
                                               ILegalEntityService legalEntityService) {
        this.propertyService = propertyService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    @Transactional
    public void handle(UpdatePropertyTeamAllCommand command) {
        LegalEntityDto buyerEntityName = command.getBuyerEntityName() != null ? this.legalEntityService.findById(command.getBuyerEntityName()) : null;

        //UPDATE Property
        this.propertyService.updateBuyerName(command.getProperty(), buyerEntityName);
    }

}
