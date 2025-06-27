package com.kynsoft.propertyacqcenter.application.command.contact.create;

import com.kynsof.share.core.domain.EUserType;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.contact.EmailAndPhoneNotNullException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.contact.EmailFormatException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.contact.EmailMustBeUniqueException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.contact.LegalEntityNotNullException;
import com.kynsoft.propertyacqcenter.domain.dto.http.CreateUserSystemRequest;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryService;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.UserSystemService;
import com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.eventPublisher.EventEmployeePublisherService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class CreateContactCommandHandler implements ICommandHandler<CreateContactCommand> {

    private final IContactService contactService;
    private final ILegalEntityService legalEntityService;
    private final ISubCategoryService subCategoryService;
    private final IEmployeeService employeeService;
    private final EventEmployeePublisherService eventEmployeePublisherService;
    private final UserSystemService userSystemService;

    public CreateContactCommandHandler(IContactService contactService,
            ILegalEntityService legalEntityService,
            ISubCategoryService subCategoryService,
            IEmployeeService employeeService,
            EventEmployeePublisherService eventEmployeePublisherService,
            UserSystemService userSystemService) {
        this.contactService = contactService;
        this.legalEntityService = legalEntityService;
        this.subCategoryService = subCategoryService;
        this.employeeService = employeeService;
        this.eventEmployeePublisherService = eventEmployeePublisherService;
        this.userSystemService = userSystemService;
    }

    @Override
    public void handle(CreateContactCommand command) {
        this.validateLegalEntityNotNull(command.getLegalEntity());
        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntity());
        SubCategoryDto subCategoryDto = this.subCategoryService.findById(command.getSubCategory());

        this.validateEmail(command.getEmail());
        this.validateEmailAndPhoneNotNull(command.getEmail(), command.getPhoneNumber());

        try {
            if (command.getIsEmployee()) {
                consumeCreateUserSystemService(command, legalEntityDto);
                this.employeeService.create(EmployeeDto
                        .builder()
                        .id(command.getId())
                        .firstName(command.getFirstName())
                        .lastName(command.getLastName())
                        .email(command.getEmail())
                        .phoneNumber(command.getPhoneNumber())
                        .position(command.getPosition())
                        .business(legalEntityDto.getBusiness())
                        .build());
            }

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

        } catch (Exception exception) {
            throw new BusinessException(DomainErrorMessage.DOCTOR_NOT_FOUND, "Ocurrió un error al crear al usuario.");
        }

//
//        this.eventEmployeePublisherService.publishRecoveryBedEvent(
//                new RabbitMqEmployeeDto(
//                        command.getId(),
//                        command.getFirstName(),
//                        command.getLastName(),
//                        command.getEmail(),
//                        legalEntityDto.getBusiness().getId()
//                )
//        );
    }

    // Método para consumir el servicio createUserSystem
    private String consumeCreateUserSystemService(CreateContactCommand command, LegalEntityDto legalEntityDto) throws IOException, URISyntaxException, InterruptedException {
        CreateUserSystemRequest createUserSystemRequest = new CreateUserSystemRequest();
        createUserSystemRequest.setUserName(command.getEmail());
        createUserSystemRequest.setEmail(command.getEmail());
        createUserSystemRequest.setName(command.getFirstName());
        createUserSystemRequest.setLastName(command.getLastName());
        //createUserSystemRequest.setPassword(PasswordGenerator.generatePassword()); // Ajusta según tus necesidades
        createUserSystemRequest.setPassword("Ecuador.*2014"); // Ajusta según tus necesidades
        createUserSystemRequest.setUserType(EUserType.SYSTEM); // Ajusta si es necesario
        createUserSystemRequest.setImage("");
        createUserSystemRequest.setBusinessId(legalEntityDto.getBusiness().getId().toString());
        return userSystemService.createUserSystem(createUserSystemRequest);
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
