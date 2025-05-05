package com.kynsoft.propertyacqcenter.application.command.companyContact.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
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
    private String department;
    private String category;
    private String notes;
    private Boolean isActive;

    public CreateCompanyContactCommand(UUID company, String firstName, String lastName, String email, String phoneNumber, String position, String department, String category, String notes, Boolean isActive) {
        this.id = UUID.randomUUID();
        this.company = company;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.department = department;
        this.category = category;
        this.notes = notes;
        this.isActive = isActive;
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
                request.getCategory(),
                request.getNotes(),
                request.getIsActive()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateCompanyContactMessage(id);
    }
}
