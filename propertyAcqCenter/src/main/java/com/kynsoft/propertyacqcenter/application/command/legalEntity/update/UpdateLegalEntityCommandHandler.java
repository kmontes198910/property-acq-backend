package com.kynsoft.propertyacqcenter.application.command.legalEntity.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class UpdateLegalEntityCommandHandler implements ICommandHandler<UpdateLegalEntityCommand> {

    private final ILegalEntityService legalEntityService;
    private final IBusinessService businessService;

    public UpdateLegalEntityCommandHandler(ILegalEntityService legalEntityService,
                                           IBusinessService businessService) {
        this.legalEntityService = legalEntityService;
        this.businessService = businessService;
    }

    @Override
    public void handle(UpdateLegalEntityCommand command) {
        BusinessDto businessDto = this.businessService.findById(command.getBusiness());
        LegalEntityDto parent = command.getParentEntityId() != null ? this.legalEntityService.findById(command.getParentEntityId()) : null;
        legalEntityService.update(new LegalEntityDto(
                command.getId(), 
                command.getName(), 
                command.getTaxId(), 
                command.getEntityType(), 
                businessDto, 
                command.getFormationState(), 
                command.getFormationDate(), 
                command.getFiscalYearEnd(), 
                command.getBusinessDescription(), 
                command.getWebsite(), 
                command.getIndustry(), 
                command.getAnnualRevenue(),
                command.getDateOfLastAnnualReport(), 
                parent, 
                command.getNotes(), 
                command.getStatus(), 
                null, 
                null,
                command.getOwner()
        ));
    }
}
