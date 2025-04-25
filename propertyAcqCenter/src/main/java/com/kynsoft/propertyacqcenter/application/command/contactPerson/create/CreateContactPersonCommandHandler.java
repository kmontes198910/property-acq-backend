package com.kynsoft.propertyacqcenter.application.command.contactPerson.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.ContactPersonDto;
import com.kynsoft.propertyacqcenter.domain.services.IContactPersonService;
import org.springframework.stereotype.Component;

@Component
public class CreateContactPersonCommandHandler implements ICommandHandler<CreateContactPersonCommand> {

    private final IContactPersonService contactPersonService;

    public CreateContactPersonCommandHandler(IContactPersonService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    @Override
    public void handle(CreateContactPersonCommand command) {
        ContactPersonDto contactPersonDto = new ContactPersonDto(
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
                command.getCreatedBy(),
                null
        );

        this.contactPersonService.create(contactPersonDto);
    }
}
