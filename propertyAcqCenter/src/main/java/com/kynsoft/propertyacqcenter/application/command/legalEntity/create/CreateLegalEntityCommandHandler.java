package com.kynsoft.propertyacqcenter.application.command.legalEntity.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.legalEntity.LegalEntityTaxIdMustBeUniqueException;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class CreateLegalEntityCommandHandler implements ICommandHandler<CreateLegalEntityCommand> {

    private final ILegalEntityService legalEntityService;
    private final IBusinessService businessService;

    public CreateLegalEntityCommandHandler(ILegalEntityService legalEntityService,
                                           IBusinessService businessService) {
        this.legalEntityService = legalEntityService;
        this.businessService = businessService;
    }

    @Override
    public void handle(CreateLegalEntityCommand command) {
        BusinessDto businessDto = this.businessService.findById(command.getBusiness());
        LegalEntityDto parent = command.getParentEntityId() != null ? this.legalEntityService.findById(command.getParentEntityId()) : null;

        this.validateTaxId(command.getTaxId());

        legalEntityService.create(new LegalEntityDto(
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
                command.getOwner(),
                command.getEntityExperience(),
                command.getEntityFico()
        ));
    }

    private void validateTaxId(String taxId) {
        if (this.legalEntityService.countByTaxId(taxId) > 0) {
            throw new LegalEntityTaxIdMustBeUniqueException(taxId);
        }
    }

}
