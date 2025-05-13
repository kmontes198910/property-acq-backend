package com.kynsoft.propertyacqcenter.application.command.contact.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryService;
import org.springframework.stereotype.Component;

@Component
public class UpdateContactCommandHandler implements ICommandHandler<UpdateContactCommand> {

    private final IContactService contactService;
    private final ILegalEntityService legalEntityService;
    private final ISubCategoryService subCategoryService;

    public UpdateContactCommandHandler(IContactService contactService, ILegalEntityService legalEntityService,
                                       ISubCategoryService subCategoryService) {
        this.contactService = contactService;
        this.legalEntityService = legalEntityService;
        this.subCategoryService = subCategoryService;
    }

    @Override
    public void handle(UpdateContactCommand command) {
        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntity());
        SubCategoryDto subCategoryDto = this.subCategoryService.findById(command.getSubCategory());
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
                command.getPersonalEmail(),
                subCategoryDto               
        ));
    }
}
