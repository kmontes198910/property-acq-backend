package com.kynsoft.propertyacqcenter.application.command.company.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.services.IContactPersonService;
import org.springframework.stereotype.Component;

@Component
public class UpdateCompanyCommandHandler implements ICommandHandler<UpdateCompanyCommand> {

    private final IContactPersonService contactPersonService;

    public UpdateCompanyCommandHandler(IContactPersonService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    @Override
    public void handle(UpdateCompanyCommand command) {
        this.contactPersonService.update(new CompanyDto(
                command.getId(),
                command.getLegalEntityId(),
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
