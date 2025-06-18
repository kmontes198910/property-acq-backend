package com.kynsoft.propertyacqcenter.application.command.employee.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class CreateEmployeeCommand implements ICommand {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private String position;
    private String department;
    private Double salary;
    private Boolean active;
    private UUID business;
    private List<UUID> roles;

    public CreateEmployeeCommand(String firstName, String lastName, String email, String phoneNumber,
                                 LocalDate hireDate, String position, String department,
                                 Double salary, Boolean active, UUID business,
                                 List<UUID> roles) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.position = position;
        this.department = department;
        this.salary = salary;
        this.active = active;
        this.business = business;
        this.roles = roles;
    }

    public static CreateEmployeeCommand fromRequest(CreateEmployeeRequest request) {
        return new CreateEmployeeCommand(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getHireDate(),
                request.getPosition(),
                request.getDepartment(),
                request.getSalary(),
                request.getActive(),
                request.getBusiness(),
                request.getRoles()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateEmployeeMessage(id);
    }
}
