package com.kynsoft.propertyacqcenter.application.command.contact.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import org.springframework.stereotype.Component;

@Component
public class UpdateContactCommandHandler implements ICommandHandler<UpdateContactCommand> {

    private final IContactService contactService;

    public UpdateContactCommandHandler(IContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public void handle(UpdateContactCommand command) {
        this.contactService.update(new ContactDto(
                command.getId(),
                command.getFirstName(),
                command.getLastName(),
                command.getEmail(),
                command.getPhoneNumber(),
                command.getPosition(),
                command.getDepartment(),
                command.getCategory(),
                command.getCompany(),
                command.getNotes(),
                command.getIsActive(),
                command.getLegalEntityId(),
                command.getBusinessId()
        ));
    }
}
