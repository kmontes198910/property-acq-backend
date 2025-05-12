package com.kynsoft.propertyacqcenter.application.command.contact.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class UpdateContactCommandHandler implements ICommandHandler<UpdateContactCommand> {

    private final IContactService contactService;
    private final ILegalEntityService legalEntityService;

    public UpdateContactCommandHandler(IContactService contactService, ILegalEntityService legalEntityService) {
        this.contactService = contactService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public void handle(UpdateContactCommand command) {
        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntity());
        this.contactService.update(new ContactDto(
                command.getId(),
                command.getFirstName(),
                command.getLastName(),
                command.getEmail(),
                command.getPhoneNumber(),
                command.getPosition(),
                command.getDepartment(),
                command.getCategory(),
                command.getNotes(),
                command.getIsActive(),
                legalEntityDto,
                command.getPersonalEmail()
        ));
    }
}
