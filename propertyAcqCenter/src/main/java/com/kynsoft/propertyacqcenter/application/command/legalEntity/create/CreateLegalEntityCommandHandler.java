package com.kynsoft.propertyacqcenter.application.command.legalEntity.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.legalEntity.LegalEntityEntityFicoException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.legalEntity.LegalEntityFormationDateException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.legalEntity.LegalEntityTaxIdMustBeUniqueException;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class CreateLegalEntityCommandHandler implements ICommandHandler<CreateLegalEntityCommand> {

    private final ILegalEntityService legalEntityService;
    private final IBusinessService businessService;

    private static final float MIN_FICO = 300;
    private static final float MAX_FICO = 850;

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
        this.validateEntityFico(command);
        this.validateFormationDate(command.getFormationDate());

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

    private void validateFormationDate(LocalDate formationDate) {
        if (formationDate.isAfter(LocalDate.now())) {
            throw new LegalEntityFormationDateException(formationDate);
        }
    }

    private void validateEntityFico(CreateLegalEntityCommand command) {
        if (command.getEntityFico() != null && (command.getEntityFico() < MIN_FICO || command.getEntityFico() > MAX_FICO)) {
            throw new LegalEntityEntityFicoException(command.getEntityFico());
        }
    }

}
