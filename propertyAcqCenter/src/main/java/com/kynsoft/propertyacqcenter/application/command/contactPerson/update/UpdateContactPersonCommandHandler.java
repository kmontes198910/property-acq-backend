package com.kynsoft.propertyacqcenter.application.command.contactPerson.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.ContactPersonDto;
import com.kynsoft.propertyacqcenter.domain.services.IContactPersonService;
import org.springframework.stereotype.Component;

@Component
public class UpdateContactPersonCommandHandler implements ICommandHandler<UpdateContactPersonCommand> {

    private final IContactPersonService contactPersonService;

    public UpdateContactPersonCommandHandler(IContactPersonService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    @Override
    public void handle(UpdateContactPersonCommand command) {
        this.contactPersonService.update(new ContactPersonDto(
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
