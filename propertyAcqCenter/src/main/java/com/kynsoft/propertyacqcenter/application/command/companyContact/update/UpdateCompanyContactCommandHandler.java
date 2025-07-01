package com.kynsoft.propertyacqcenter.application.command.companyContact.update;

import com.kynsof.share.core.domain.EUserType;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import com.kynsoft.propertyacqcenter.domain.dto.http.CreateUserSystemRequest;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.UserSystemService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class UpdateCompanyContactCommandHandler implements ICommandHandler<UpdateCompanyContactCommand> {

    private final ICompanyContactService companyContactService;
    private final ICompanyService companyService;
    private final IEmployeeService employeeService;
    private final UserSystemService userSystemService;

    public UpdateCompanyContactCommandHandler(ICompanyContactService companyContactService,
            ICompanyService companyService,
            UserSystemService userSystemService,
            IEmployeeService employeeService) {
        this.companyContactService = companyContactService;
        this.companyService = companyService;
        this.userSystemService = userSystemService;
        this.employeeService = employeeService;
    }

    @Override
    public void handle(UpdateCompanyContactCommand command) {
        CompanyDto companyDto = this.companyService.findById(command.getCompany());
        this.companyContactService.validateEmail(command.getEmail(), command.getId());
        //this.companyContactService.validatePersonEmail(command.getPersonalEmail(), command.getId());
        try {
            if (command.getIsEmployee() && this.employeeService.countById(command.getId()) == 0) {
                consumeCreateUserSystemService(command, companyDto.getBusiness());

                this.employeeService.create(EmployeeDto
                        .builder()
                        .id(command.getId())
                        .firstName(command.getFirstName())
                        .lastName(command.getLastName())
                        .email(command.getEmail())
                        .phoneNumber(command.getPhoneNumber())
                        .position(command.getPosition())
                        .business(companyDto.getBusiness())
                        .build());
            }
            companyContactService.update(CompanyContactDto.builder()
                    .id(command.getId())
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
                    .birthDate(command.getBirthDate())
                    .build()
            );
        } catch (Exception exception) {
            throw new BusinessException(DomainErrorMessage.DOCTOR_NOT_FOUND, "Ocurrió un error al crear al usuario.");
        }
    }

    // Método para consumir el servicio createUserSystem
    private String consumeCreateUserSystemService(UpdateCompanyContactCommand command, BusinessDto businessDto) throws IOException, URISyntaxException, InterruptedException {
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
