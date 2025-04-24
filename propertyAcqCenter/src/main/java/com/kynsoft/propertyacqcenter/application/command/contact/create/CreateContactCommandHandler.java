package com.kynsoft.propertyacqcenter.application.command.contact.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import org.springframework.stereotype.Component;

@Component
public class CreateContactCommandHandler implements ICommandHandler<CreateContactCommand> {

    private final IContactService contactService;

    public CreateContactCommandHandler(IContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public void handle(CreateContactCommand command) {
        ContactDto contactDto = ContactDto.builder()
                .id(command.getId())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .phoneNumber(command.getPhoneNumber())
                .position(command.getPosition())
                .department(command.getDepartment())
                .category(command.getCategory())
                .company(command.getCompany())
                .notes(command.getNotes())
                .isActive(command.getIsActive())
                .legalEntityId(command.getLegalEntityId())
                .businessId(command.getBusinessId())
                .build();

        this.contactService.create(contactDto);
    }
}
