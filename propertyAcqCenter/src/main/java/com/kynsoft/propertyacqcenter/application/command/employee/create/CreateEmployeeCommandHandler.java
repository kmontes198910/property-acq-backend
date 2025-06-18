package com.kynsoft.propertyacqcenter.application.command.employee.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.dto.RabbitMqEmployeeDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.eventPublisher.EventEmployeePublisherService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class CreateEmployeeCommandHandler implements ICommandHandler<CreateEmployeeCommand> {

    private final IEmployeeService employeeService;
    private final EventEmployeePublisherService eventEmployeePublisherService;

    private final IBusinessService businessService;

    public CreateEmployeeCommandHandler(IEmployeeService employeeService, EventEmployeePublisherService eventEmployeePublisherService, IBusinessService businessService) {
        this.employeeService = employeeService;
        this.eventEmployeePublisherService = eventEmployeePublisherService;
        this.businessService = businessService;
    }

    @Override
    @Transactional
    public void handle(CreateEmployeeCommand command) {
        BusinessDto businessDto = command.getBusiness() != null ? this.businessService.findById(command.getBusiness()) : null;

        this.employeeService.validateEmail(command.getEmail(), command.getId());

        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(command.getId())
                .active(command.getActive())
                .email(command.getEmail())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .phoneNumber(command.getPhoneNumber())
                .hireDate(command.getHireDate())
                .position(command.getPosition())
                .department(command.getDepartment())
                .salary(command.getSalary())
                .business(businessDto)
                .build();

        this.employeeService.create(employeeDto);
       this.eventEmployeePublisherService.publishRecoveryBedEvent(
               new RabbitMqEmployeeDto(
                       employeeDto.getId(),
                       employeeDto.getFirstName(),
                       employeeDto.getLastName(),
                       employeeDto.getEmail(),
                       employeeDto.getBusiness().getId()
               )
       );
    }

}
