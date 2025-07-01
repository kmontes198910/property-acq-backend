package com.kynsoft.propertyacqcenter.application.command.contact.update;

import com.kynsof.share.core.domain.EUserType;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.dto.http.CreateUserSystemRequest;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryService;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.UserSystemService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class UpdateContactCommandHandler implements ICommandHandler<UpdateContactCommand> {

    private final IContactService contactService;
    private final ILegalEntityService legalEntityService;
    private final ISubCategoryService subCategoryService;
    private final IEmployeeService employeeService;
    private final UserSystemService userSystemService;

    public UpdateContactCommandHandler(IContactService contactService, ILegalEntityService legalEntityService,
            ISubCategoryService subCategoryService,
            UserSystemService userSystemService,
            IEmployeeService employeeService) {
        this.contactService = contactService;
        this.legalEntityService = legalEntityService;
        this.subCategoryService = subCategoryService;
        this.userSystemService = userSystemService;
        this.employeeService = employeeService;
    }

    @Override
    public void handle(UpdateContactCommand command) {
        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntity());
        SubCategoryDto subCategoryDto = this.subCategoryService.findById(command.getSubCategory());
        try {
            if (command.getIsEmployee() && this.employeeService.countById(command.getId()) == 0) {
                consumeCreateUserSystemService(command, legalEntityDto.getBusiness());

                System.err.println("||||||||||||||||||||||||||||||||||||||||");
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
            } else {
                System.err.println("********************");
            }
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
        } catch (Exception exception) {
            throw new BusinessException(DomainErrorMessage.DOCTOR_NOT_FOUND, "Ocurrió un error al crear al usuario.");
        }
    }

    // Método para consumir el servicio createUserSystem
    private String consumeCreateUserSystemService(UpdateContactCommand command, BusinessDto businessDto) throws IOException, URISyntaxException, InterruptedException {
        CreateUserSystemRequest createUserSystemRequest = new CreateUserSystemRequest();
        createUserSystemRequest.setId(command.getId());
        createUserSystemRequest.setUserName(command.getEmail());
        createUserSystemRequest.setEmail(command.getEmail());
        createUserSystemRequest.setName(command.getFirstName());
        createUserSystemRequest.setLastName(command.getLastName());
        //createUserSystemRequest.setPassword(PasswordGenerator.generatePassword()); // Ajusta según tus necesidades
        createUserSystemRequest.setPassword(generateAlphaNumericPassword()); // Ajusta según tus necesidades
        createUserSystemRequest.setUserType(EUserType.SYSTEM); // Ajusta si es necesario
        createUserSystemRequest.setImage("");
        createUserSystemRequest.setBusinessId(businessDto.getId().toString());
        return userSystemService.createUserSystem(createUserSystemRequest);
    }

    public String generateAlphaNumericPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

}
