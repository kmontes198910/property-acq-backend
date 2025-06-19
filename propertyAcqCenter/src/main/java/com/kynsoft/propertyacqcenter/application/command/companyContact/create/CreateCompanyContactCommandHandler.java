package com.kynsoft.propertyacqcenter.application.command.companyContact.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.dto.RabbitMqEmployeeDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.eventPublisher.EventEmployeePublisherService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateCompanyContactCommandHandler implements ICommandHandler<CreateCompanyContactCommand> {

    private final ICompanyContactService companyContactService;
    private final ICompanyService companyService;
    private final IEmployeeService employeeService;
    private final IBusinessService businessService;
    private final EventEmployeePublisherService eventEmployeePublisherService;

    public CreateCompanyContactCommandHandler(ICompanyContactService companyContactService,
            ICompanyService companyService,
            IEmployeeService employeeService,
            IBusinessService businessService,
            EventEmployeePublisherService eventEmployeePublisherService) {
        this.companyContactService = companyContactService;
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.businessService = businessService;
        this.eventEmployeePublisherService = eventEmployeePublisherService;
    }

    @Override
    @Transactional
    public void handle(CreateCompanyContactCommand command) {
        CompanyDto companyDto = this.companyService.findById(command.getCompany());
        BusinessDto businessDto = this.businessService.findById(companyDto.getBusiness().getId());

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
                .business(businessDto)
                .build());

        this.eventEmployeePublisherService.publishRecoveryBedEvent(
                new RabbitMqEmployeeDto(
                        command.getId(),
                        command.getFirstName(),
                        command.getLastName(),
                        command.getEmail(),
                        businessDto.getId()
                )
        );
    }
}
