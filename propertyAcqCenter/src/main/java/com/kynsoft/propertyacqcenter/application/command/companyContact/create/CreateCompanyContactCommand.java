package com.kynsoft.propertyacqcenter.application.command.companyContact.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.DepartmentType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateCompanyContactCommand implements ICommand {

    private UUID id;
    private UUID company;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String position;
    private DepartmentType department;
    private String notes;
    private Boolean isActive;
    private String personalEmail;
    private LocalDate birthDate;

    private Boolean isEmployee;

    public CreateCompanyContactCommand(UUID company, String firstName, String lastName, 
                                       String email, String phoneNumber, String position, 
                                       DepartmentType department, String notes, 
                                       Boolean isActive, String personalEmail, 
                                       LocalDate birthDate, Boolean isEmployee) {
        this.id = UUID.randomUUID();
        this.company = company;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.department = department;
        this.notes = notes;
        this.isActive = isActive;
        this.personalEmail = personalEmail;
        this.birthDate = birthDate;
        this.isEmployee = isEmployee;
    }

    public static CreateCompanyContactCommand fromRequest(CreateCompanyContactRequest request) {
        return new CreateCompanyContactCommand(
                request.getCompany(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getPosition(),
                request.getDepartment(),
                request.getNotes(),
                request.getIsActive(),
                request.getPersonalEmail(),
                request.getBirthDate(),
                request.getIsEmployee()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateCompanyContactMessage(id);
    }
}
