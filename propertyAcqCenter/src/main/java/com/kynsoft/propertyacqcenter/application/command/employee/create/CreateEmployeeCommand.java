package com.kynsoft.propertyacqcenter.application.command.employee.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;


/**
 * CreateEmployeeCommand is a command class that implements the ICommand interface.
 * It is used to create a new employee in the system.
 * The class contains a method to get the command message associated with the command.
 */
@Getter
public class CreateEmployeeCommand implements ICommand {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate hireDate;
    private String position;
    private String department;
    private String employeeNumber;
    private Double salary;
    private Boolean active;
    private UUID business;

    /**
     * Constructor for CreateEmployeeCommand.
     *
     * @param firstName     The first name of the employee.
     * @param lastName      The last name of the employee.
     * @param email         The email address of the employee.
     * @param phoneNumber   The phone number of the employee.
     * @param hireDate      The hire date of the employee.
     * @param position      The position of the employee.
     * @param department    The department of the employee.
     * @param employeeNumber The employee number.
     * @param salary        The salary of the employee.
     * @param active        Indicates if the employee is active or not.
     * @param business      The business id associated with the employee.
     */
    public CreateEmployeeCommand(String firstName, String lastName, String email, String phoneNumber,
                                 LocalDate hireDate, String position, String department, String employeeNumber,
                                 Double salary, Boolean active, UUID business) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.position = position;
        this.department = department;
        this.employeeNumber = employeeNumber;
        this.salary = salary;
        this.active = active;
        this.business = business;
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
                request.getEmployeeNumber(),
                request.getSalary(),
                request.getActive(),
                request.getBusiness()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateEmployeeMessage(id);
    }
}
