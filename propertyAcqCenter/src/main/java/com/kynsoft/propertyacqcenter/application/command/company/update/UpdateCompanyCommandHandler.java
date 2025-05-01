package com.kynsoft.propertyacqcenter.application.command.company.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import org.springframework.stereotype.Component;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyTypeService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;

@Component
public class UpdateCompanyCommandHandler implements ICommandHandler<UpdateCompanyCommand> {

    private final ICompanyService contactPersonService;
    private final ILegalEntityService legalEntityService;
    private final ICompanyTypeService companyTypeService;

    public UpdateCompanyCommandHandler(ICompanyService contactPersonService, 
                                       ILegalEntityService legalEntityService,
                                       ICompanyTypeService companyTypeService) {
        this.contactPersonService = contactPersonService;
        this.legalEntityService = legalEntityService;
        this.companyTypeService = companyTypeService;
    }

    @Override
    public void handle(UpdateCompanyCommand command) {
        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntityId());
        CompanyTypeDto companyTypeDto = this.companyTypeService.findById(command.getCompanyType());
        this.contactPersonService.update(new CompanyDto(
                command.getId(),
                legalEntityDto,
                companyTypeDto,
                command.getFirstName(),
                command.getLastName(),
                command.getRole(),
                command.getEmail(),
                command.getPhone(),
                command.getCellPhone(),
                command.getTitle(),
                command.getDateOfBirth(),
                command.getPersonalTaxId(),
                command.getNationality(),
                command.getPersonalAddress(),
                command.getCity(),
                command.getState(),
                command.getZipCode(),
                command.getPersonalEmail(),
                command.getIsPrimary(),
                command.getOwnershipPercentage(),
                command.getSignatureAuthority(),
                command.getNotes(),
                null,
                command.getUpdatedBy()
        ));
    }
}
