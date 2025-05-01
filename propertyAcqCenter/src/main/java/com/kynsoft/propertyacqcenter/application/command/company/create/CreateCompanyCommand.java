package com.kynsoft.propertyacqcenter.application.command.company.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.ContactRole;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class CreateCompanyCommand implements ICommand {

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
    private UUID createdBy;

    public CreateCompanyCommand(UUID legalEntityId, String firstName, String lastName, ContactRole role, String email, String phone, String cellPhone, String title, LocalDate dateOfBirth, String personalTaxId, String nationality, String personalAddress, String city, String state, String zipCode, String personalEmail, Boolean isPrimary, Double ownershipPercentage, Boolean signatureAuthority, String notes, UUID createdBy) {
        this.id = UUID.randomUUID();
        this.legalEntityId = legalEntityId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.cellPhone = cellPhone;
        this.title = title;
        this.dateOfBirth = dateOfBirth;
        this.personalTaxId = personalTaxId;
        this.nationality = nationality;
        this.personalAddress = personalAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.personalEmail = personalEmail;
        this.isPrimary = isPrimary;
        this.ownershipPercentage = ownershipPercentage;
        this.signatureAuthority = signatureAuthority;
        this.notes = notes;
        this.createdBy = createdBy;
    }

    public static CreateCompanyCommand fromRequest(CreateCompanyRequest request) {
        return new CreateCompanyCommand(
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
                request.getCreatedBy()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateCompanyMessage(id);
    }
}