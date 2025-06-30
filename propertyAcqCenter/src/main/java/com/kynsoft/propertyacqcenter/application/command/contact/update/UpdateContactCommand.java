package com.kynsoft.propertyacqcenter.application.command.contact.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import com.kynsoft.propertyacqcenter.domain.enums.DepartmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class UpdateContactCommand implements ICommand {

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

    public static UpdateContactCommand fromRequest(UUID id, UpdateContactRequest request) {
        return new UpdateContactCommand(
                id,
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
        return new UpdateContactMessage(id);
    }
}
