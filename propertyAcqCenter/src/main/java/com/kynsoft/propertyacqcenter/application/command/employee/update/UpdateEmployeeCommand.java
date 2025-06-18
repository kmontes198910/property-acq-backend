package com.kynsoft.propertyacqcenter.application.command.employee.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateEmployeeCommand implements ICommand {

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

    public static UpdateEmployeeCommand fromRequest(UUID id, UpdateEmployeeRequest request) {
        return new UpdateEmployeeCommand(
                id,
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
        return new UpdateEmployeeMessage(id);
    }
}
