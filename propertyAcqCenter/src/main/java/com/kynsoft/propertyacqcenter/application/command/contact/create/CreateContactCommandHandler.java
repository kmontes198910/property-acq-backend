package com.kynsoft.propertyacqcenter.application.command.contact.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.contact.EmailAndPhoneNotNullException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.contact.EmailFormatException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.contact.EmailMustBeUniqueException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.contact.LegalEntityNotNullException;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryService;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class CreateContactCommandHandler implements ICommandHandler<CreateContactCommand> {

    private final IContactService contactService;
    private final ILegalEntityService legalEntityService;
    private final ISubCategoryService subCategoryService;

    public CreateContactCommandHandler(IContactService contactService, ILegalEntityService legalEntityService, ISubCategoryService subCategoryService) {
        this.contactService = contactService;
        this.legalEntityService = legalEntityService;
        this.subCategoryService = subCategoryService;
    }

    @Override
    public void handle(CreateContactCommand command) {
        this.validateLegalEntityNotNull(command.getLegalEntity());
        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntity());
        SubCategoryDto subCategoryDto = this.subCategoryService.findById(command.getSubCategory());

        this.validateEmail(command.getEmail());
        this.validateEmailAndPhoneNotNull(command.getEmail(), command.getPhoneNumber());

        ContactDto contactDto = ContactDto.builder()
                .id(command.getId())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .phoneNumber(command.getPhoneNumber())
                .position(command.getPosition())
                .department(command.getDepartment())
                .category(command.getCategory())
                .notes(command.getNotes())
                .isActive(command.getIsActive())
                .legalEntity(legalEntityDto)
                .personalEmail(command.getPersonalEmail())
                .subCategory(subCategoryDto)
                .build();

        this.contactService.create(contactDto);
    }

    private void validateEmail(String email) {
        if (email != null) {
            if (this.contactService.countByEmail(email) > 0) {
                throw new EmailMustBeUniqueException(email);
            }
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(emailRegex);
            if (!pattern.matcher(email).matches()) {
                throw new EmailFormatException(email);
            }
        }
    }

    private void validateEmailAndPhoneNotNull(String email, String phone) {
        if (email == null && phone == null) {
            throw new EmailAndPhoneNotNullException();
        }
    }

    private void validateLegalEntityNotNull(UUID legalEntity) {
        if (legalEntity == null) {
            throw new LegalEntityNotNullException();
        }
    }
}
