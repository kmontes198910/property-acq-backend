package com.kynsoft.propertyacqcenter.application.command.contact.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import com.kynsoft.propertyacqcenter.domain.enums.DepartmentType;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
public class CreateContactCommand implements ICommand {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String position;
    private DepartmentType department;
    private ContactType category;
    private String notes;
    private Boolean isActive;
    private UUID legalEntity;
    private String personalEmail;
    private UUID subCategory;

    private Boolean isEmployee;

    public CreateContactCommand(String firstName, String lastName, String email, 
            String phoneNumber, String position, DepartmentType department, ContactType category, 
            String notes, Boolean isActive, UUID legalEntityId, String personalEmail, UUID subCategory,
            Boolean isEmployee) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.department = department;
        this.category = category;
        this.notes = notes;
        this.isActive = isActive;
        this.legalEntity = legalEntityId;
        this.personalEmail = personalEmail;
        this.subCategory = subCategory;
        this.isEmployee = isEmployee;
    }

    public static CreateContactCommand fromRequest(CreateContactRequest request){
        return new CreateContactCommand(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getPosition(),
                request.getDepartment(),
                request.getCategory(),
                request.getNotes(),
                request.getIsActive(),
                request.getLegalEntity(),
                request.getPersonalEmail(),
                request.getSubCategory(),
                request.getIsEmployee()
        );
    }


    @Override
    public ICommandMessage getMessage() {
        return new CreateContactMessage(id);
    }
}
