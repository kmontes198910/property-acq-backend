package com.kynsoft.propertyacqcenter.application.command.contact.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class CreateContactCommandHandler implements ICommandHandler<CreateContactCommand> {

    private final IContactService contactService;
    private final ILegalEntityService legalEntityService;

    public CreateContactCommandHandler(IContactService contactService, ILegalEntityService legalEntityService) {
        this.contactService = contactService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public void handle(CreateContactCommand command) {
        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntity());

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
                .legalEntity(legalEntityDto)
                .build();

        this.contactService.create(contactDto);
    }
}
