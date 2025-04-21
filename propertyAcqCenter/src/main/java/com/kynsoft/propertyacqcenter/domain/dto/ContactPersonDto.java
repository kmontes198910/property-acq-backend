package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.infrastructure.entity.enums.ContactRole;
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
public class ContactPersonDto {
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
}