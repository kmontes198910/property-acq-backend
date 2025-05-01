package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.enums.ContactRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CompanyResponse implements IResponse {

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public CompanyResponse(CompanyDto contactPersonDto) {
        this.id = contactPersonDto.getId();
        this.legalEntityId = contactPersonDto.getLegalEntityId();
        this.firstName = contactPersonDto.getFirstName();
        this.lastName = contactPersonDto.getLastName();
        this.role = contactPersonDto.getRole();
        this.email = contactPersonDto.getEmail();
        this.phone = contactPersonDto.getPhone();
        this.cellPhone = contactPersonDto.getCellPhone();
        this.title = contactPersonDto.getTitle();
        this.dateOfBirth = contactPersonDto.getDateOfBirth();
        this.personalTaxId = contactPersonDto.getPersonalTaxId();
        this.nationality = contactPersonDto.getNationality();
        this.personalAddress = contactPersonDto.getPersonalAddress();
        this.city = contactPersonDto.getCity();
        this.state = contactPersonDto.getState();
        this.zipCode = contactPersonDto.getZipCode();
        this.personalEmail = contactPersonDto.getPersonalEmail();
        this.isPrimary = contactPersonDto.getIsPrimary();
        this.ownershipPercentage = contactPersonDto.getOwnershipPercentage();
        this.signatureAuthority = contactPersonDto.getSignatureAuthority();
        this.notes = contactPersonDto.getNotes();
        this.createdAt = contactPersonDto.getCreatedAt();
        this.updatedAt = contactPersonDto.getUpdatedAt();
        this.createdBy = contactPersonDto.getCreatedBy();
        this.updatedBy = contactPersonDto.getUpdatedBy();
    }
}
