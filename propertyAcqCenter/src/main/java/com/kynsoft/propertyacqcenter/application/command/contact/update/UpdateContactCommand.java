package com.kynsoft.propertyacqcenter.application.command.contact.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateContactCommand implements ICommand {

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
    private UUID legalEntity;

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
                request.getCompany(),
                request.getNotes(),
                request.getIsActive(),
                request.getLegalEntity()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateContactMessage(id);
    }
}
