package com.kynsoft.propertyacqcenter.application.command.employee.create;

import com.kynsof.share.core.domain.EUserType;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;

import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.eventPublisher.EventEmployeePublisherService;
import jakarta.transaction.Transactional;

import com.kynsoft.propertyacqcenter.domain.dto.ManageRolDto;
import com.kynsoft.propertyacqcenter.domain.dto.http.CreateUserSystemRequest;
import com.kynsoft.propertyacqcenter.domain.services.IManageRoleService;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.UserSystemService;
import java.io.IOException;
import java.net.URISyntaxException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class CreateEmployeeCommandHandler implements ICommandHandler<CreateEmployeeCommand> {

    private final IEmployeeService employeeService;
    private final EventEmployeePublisherService eventEmployeePublisherService;

    private final IBusinessService businessService;
    private final IManageRoleService roleService;
    private final UserSystemService userSystemService;

    public CreateEmployeeCommandHandler(IEmployeeService employeeService,
            EventEmployeePublisherService eventEmployeePublisherService, IBusinessService businessService,
            IManageRoleService roleService,
            UserSystemService userSystemService) {

        this.employeeService = employeeService;
        this.eventEmployeePublisherService = eventEmployeePublisherService;

        this.businessService = businessService;
        this.roleService = roleService;
        this.userSystemService = userSystemService;
    }

    @Override
    @Transactional
    public void handle(CreateEmployeeCommand command) {
        BusinessDto businessDto = command.getBusiness() != null ? this.businessService.findById(command.getBusiness()) : null;

        this.employeeService.validateEmail(command.getEmail(), command.getId());

        try {
            consumeCreateUserSystemService(command, businessDto);
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
                    .roles(command.getRoles() != null ? this.get(command.getRoles()) : null)
                    .build();

            this.employeeService.create(employeeDto);
        } catch (Exception exception) {
            throw new BusinessException(DomainErrorMessage.DOCTOR_NOT_FOUND, "Ocurrió un error al crear al usuario.");
        }

//        this.eventEmployeePublisherService.publishRecoveryBedEvent(
//                new RabbitMqEmployeeDto(
//                        employeeDto.getId(),
//                        employeeDto.getFirstName(),
//                        employeeDto.getLastName(),
//                        employeeDto.getEmail(),
//                        employeeDto.getBusiness().getId()
//                )
//        );
    }

    // Método para consumir el servicio createUserSystem
    private String consumeCreateUserSystemService(CreateEmployeeCommand command, BusinessDto businessDto) throws IOException, URISyntaxException, InterruptedException {
        CreateUserSystemRequest createUserSystemRequest = new CreateUserSystemRequest();
        createUserSystemRequest.setId(command.getId());
        createUserSystemRequest.setUserName(command.getEmail());
        createUserSystemRequest.setEmail(command.getEmail());
        createUserSystemRequest.setName(command.getFirstName());
        createUserSystemRequest.setLastName(command.getLastName());
        //createUserSystemRequest.setPassword(PasswordGenerator.generatePassword()); // Ajusta según tus necesidades
        createUserSystemRequest.setPassword("Ecuador.*2014"); // Ajusta según tus necesidades
        createUserSystemRequest.setUserType(EUserType.SYSTEM); // Ajusta si es necesario
        createUserSystemRequest.setImage("");
        createUserSystemRequest.setBusinessId(businessDto.getId().toString());
        return userSystemService.createUserSystem(createUserSystemRequest);
    }

    private List<ManageRolDto> get(List<UUID> ids) {
        return ids.stream()
                .map(this.roleService::findById)
                .collect(Collectors.toList());
    }
}
