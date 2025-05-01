package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.ContactRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
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

    public CompanyDto(UUID id, UUID legalEntityId, String firstName, String lastName, ContactRole role, String email, String phone, String cellPhone, String title, LocalDate dateOfBirth, String personalTaxId, String nationality, String personalAddress, String city, String state, String zipCode, String personalEmail, Boolean isPrimary, Double ownershipPercentage, Boolean signatureAuthority, String notes, UUID createdBy, UUID updatedBy) {
        this.id = id;
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
        this.updatedBy = updatedBy;
    }
}