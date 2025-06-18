package com.kynsoft.propertyacqcenter.application.command.companyContact.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateCompanyContactCommandHandler implements ICommandHandler<CreateCompanyContactCommand> {

    private final ICompanyContactService companyContactService;
    private final ICompanyService companyService;
    private final IEmployeeService employeeService;

    public CreateCompanyContactCommandHandler(ICompanyContactService companyContactService, 
                                              ICompanyService companyService,
                                              IEmployeeService employeeService) {
        this.companyContactService = companyContactService;
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @Override
    @Transactional
    public void handle(CreateCompanyContactCommand command) {
        CompanyDto companyDto = this.companyService.findById(command.getCompany());
        this.companyContactService.validateEmail(command.getEmail(), command.getId());
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
                .build()
        );
        this.employeeService.create(EmployeeDto
                .builder()
                .id(command.getId())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .phoneNumber(command.getPhoneNumber())
                .position(command.getPosition())
                .build());
    }
}
