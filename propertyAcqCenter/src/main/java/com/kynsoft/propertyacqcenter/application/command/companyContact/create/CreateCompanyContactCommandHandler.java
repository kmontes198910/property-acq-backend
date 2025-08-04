package com.kynsoft.propertyacqcenter.application.command.companyContact.create;

import com.kynsof.share.core.domain.EUserType;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.dto.http.CreateUserSystemRequest;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryService;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.UserSystemService;
import com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.eventPublisher.EventEmployeePublisherService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.SecureRandom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class CreateCompanyContactCommandHandler implements ICommandHandler<CreateCompanyContactCommand> {

    private final ICompanyContactService companyContactService;
    private final ICompanyService companyService;
    private final IEmployeeService employeeService;
    private final IBusinessService businessService;
    private final EventEmployeePublisherService eventEmployeePublisherService;
    private final UserSystemService userSystemService;
    private final ILegalEntityService legalEntityService;
    private final ISubCategoryService subCategoryService;

    public CreateCompanyContactCommandHandler(ICompanyContactService companyContactService,
                                                ICompanyService companyService,
                                                IEmployeeService employeeService,
                                                IBusinessService businessService,
                                                EventEmployeePublisherService eventEmployeePublisherService,
                                                UserSystemService userSystemService,
                                                ILegalEntityService legalEntityService,
                                                ISubCategoryService subCategoryService) {
        this.companyContactService = companyContactService;
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.businessService = businessService;
        this.eventEmployeePublisherService = eventEmployeePublisherService;
        this.userSystemService = userSystemService;
        this.legalEntityService = legalEntityService;
        this.subCategoryService = subCategoryService;
    }

    @Override
    @Transactional
    public void handle(CreateCompanyContactCommand command) {
        CompanyDto companyDto = command.getCompany() != null ? this.companyService.findById(command.getCompany()) : null;
        LegalEntityDto legalEntityDto = command.getLegalEntity() != null ? this.legalEntityService.findById(command.getLegalEntity()) : null;
        SubCategoryDto subCategoryDto = command.getSubCategory() != null ? this.subCategoryService.findById(command.getSubCategory()) : null;

        BusinessDto businessDto = null;
        if (companyDto != null) {
            businessDto = this.businessService.findById(companyDto.getBusiness().getId());
        } else if (legalEntityDto != null) {
            businessDto = this.businessService.findById(legalEntityDto.getBusiness().getId());
        }

        this.companyContactService.validateEmail(command.getEmail(), command.getId());

        try {
            log.error("Creating user system for contact: {}", command.getEmail());
            if (command.getIsEmployee()) {
                consumeCreateUserSystemService(command, businessDto);
                
                this.employeeService.create(EmployeeDto
                        .builder()
                        .id(command.getId())
                        .firstName(command.getFirstName())
                        .lastName(command.getLastName())
                        .email(command.getEmail())
                        .phoneNumber(command.getPhoneNumber())
                        .position(command.getPosition())
                        .business(businessDto)
                        .build());
            }
            log.error("User system created successfully for contact: {}", command.getEmail());
            //this.companyContactService.validatePersonEmail(command.getPersonalEmail(), command.getId());
            companyContactService.create(CompanyContactDto.builder()
                    .id(command.getId())
                    .birthDate(command.getBirthDate())
                    .company(companyDto)
                    .department(command.getDepartment())
                    .email(command.getEmail())
                    .firstName(command.getFirstName())
                    .lastName(command.getLastName())
                    .isActive(command.getIsActive())
                    .notes(command.getNotes())
                    .phoneNumber(command.getPhoneNumber())
                    .position(command.getPosition())
                    .personalEmail(command.getPersonalEmail())
                    .mailingAddress(command.getMailingAddress())
                    .isEmployee(command.getIsEmployee())
                    .legalEntity(legalEntityDto)
                    .subCategory(subCategoryDto)
                    .category(command.getCategory())
                    .type(command.getType())
                    .build()
            );
        } catch (Exception exception) {
            log.error("Error creating user system for contact: {}", command.getEmail(), exception);
            throw new BusinessException(DomainErrorMessage.DOCTOR_NOT_FOUND, "Ocurrió un error al crear al usuario.");
        }

//
//        this.eventEmployeePublisherService.publishRecoveryBedEvent(
//                new RabbitMqEmployeeDto(
//                        command.getId(),
//                        command.getFirstName(),
//                        command.getLastName(),
//                        command.getEmail(),
//                        businessDto.getId()
//                )
//        );
    }

    // Método para consumir el servicio createUserSystem
    private String consumeCreateUserSystemService(CreateCompanyContactCommand command, BusinessDto businessDto) throws IOException, URISyntaxException, InterruptedException {
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
