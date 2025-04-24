package com.kynsoft.propertyacqcenter.application.command.contact.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateContactCommand implements ICommand {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String position;
    private String department;
    private String category;
    private String company;
    private String notes;
    private Boolean isActive;
    private UUID legalEntityId;
    private UUID businessId;

    public CreateContactCommand(String firstName, String lastName, String email, String phoneNumber, String position, String department, String category, String company, String notes, Boolean isActive,  UUID legalEntityId, UUID businessId) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.department = department;
        this.category = category;
        this.company = company;
        this.notes = notes;
        this.isActive = isActive;
        this.legalEntityId = legalEntityId;
        this.businessId = businessId;
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
                request.getCompany(),
                request.getNotes(),
                request.getIsActive(),
                request.getLegalEntityId(),
                request.getBusinessId()
        );
    }


    @Override
    public ICommandMessage getMessage() {
        return new CreateContactMessage(id);
    }
}
