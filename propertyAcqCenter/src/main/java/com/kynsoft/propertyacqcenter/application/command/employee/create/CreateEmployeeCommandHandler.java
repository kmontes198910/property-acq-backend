package com.kynsoft.propertyacqcenter.application.command.employee.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import com.kynsoft.propertyacqcenter.domain.dto.ManageRolDto;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import com.kynsoft.propertyacqcenter.domain.services.IManageRoleService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CreateEmployeeCommandHandler implements ICommandHandler<CreateEmployeeCommand> {

    private final IEmployeeService employeeService;

    private final IBusinessService businessService;

    private final IManageRoleService roleService;

    public CreateEmployeeCommandHandler(IEmployeeService employeeService, 
                                        IBusinessService businessService,
                                        IManageRoleService roleService) {
        this.employeeService = employeeService;
        this.businessService = businessService;
        this.roleService = roleService;
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
                .roles(command.getRoles() != null ? this.get(command.getRoles()) : null)
                .build();

        this.employeeService.create(employeeDto);
    }

    private List<ManageRolDto> get(List<UUID> ids) {
        return ids.stream()
            .map(this.roleService::findById)
            .collect(Collectors.toList());
    }
}
