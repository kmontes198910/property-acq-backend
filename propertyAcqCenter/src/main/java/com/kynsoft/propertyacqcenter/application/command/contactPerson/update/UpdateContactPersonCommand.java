package com.kynsoft.propertyacqcenter.application.command.contactPerson.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.ContactRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateContactPersonCommand implements ICommand {

    private UUID id;
    private UUID legalEntityId;
    private String firstName;
    private String lastName;
    private ContactRole role;
    private String email;
    private String phone;
    private String cellPhone;
    private String title;
    private LocalDate dateOfBirth;
    private String personalTaxId;
    private String nationality;
    private String personalAddress;
    private String city;
    private String state;
    private String zipCode;
    private String personalEmail;
    private Boolean isPrimary;
    private Double ownershipPercentage;
    private Boolean signatureAuthority;
    private String notes;
    private UUID updatedBy;

    public static UpdateContactPersonCommand fromRequest(UUID id, UpdateContactPersonRequest request) {
        return new UpdateContactPersonCommand(
                id,
                request.getLegalEntityId(),
                request.getFirstName(),
                request.getLastName(),
                request.getRole(),
                request.getEmail(),
                request.getPhone(),
                request.getCellPhone(),
                request.getTitle(),
                request.getDateOfBirth(),
                request.getPersonalTaxId(),
                request.getNationality(),
                request.getPersonalAddress(),
                request.getCity(),
                request.getState(),
                request.getZipCode(),
                request.getPersonalEmail(),
                request.getIsPrimary(),
                request.getOwnershipPercentage(),
                request.getSignatureAuthority(),
                request.getNotes(),
                request.getUpdatedBy()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateContactPersonMessage(id);
    }
}
