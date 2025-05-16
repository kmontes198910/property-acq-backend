package com.kynsoft.propertyacqcenter.application.command.employee.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.EmployeeDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.employee.EmployeeEmailFormatException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.employee.EmployeeEmailMustBeUniqueException;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import jakarta.transaction.Transactional;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class CreateEmployeeCommandHandler implements ICommandHandler<CreateEmployeeCommand> {

    private final IEmployeeService employeeService;

    private final IBusinessService businessService;

    public CreateEmployeeCommandHandler(IEmployeeService employeeService, IBusinessService businessService) {
        this.employeeService = employeeService;
        this.businessService = businessService;
    }

    @Override
    @Transactional
    public void handle(CreateEmployeeCommand command) {
        BusinessDto businessDto = command.getBusiness() != null ? this.businessService.findById(command.getBusiness()) : null;

        this.validateEmail(command.getEmail());

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
    }

    private void validateEmail(String email) {
        if (this.employeeService.countByEmail(email) > 0) {
            throw new EmployeeEmailMustBeUniqueException(email);
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            throw new EmployeeEmailFormatException(email);
        }
    }

}
