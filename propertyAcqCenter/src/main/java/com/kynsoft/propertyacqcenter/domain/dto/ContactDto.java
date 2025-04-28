package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDto {

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LegalEntityDto legalEntity;
    private BusinessDto business;

    public ContactDto(UUID id, String firstName, String lastName, String email, String phoneNumber, String position, String department, String category, String company, String notes, Boolean isActive, LegalEntityDto legalEntity, BusinessDto business) {
        this.id = id;
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
        this.business = business;
        this.legalEntity = legalEntity;
    }

    /**
     * Obtiene el nombre completo del contacto
     * @return Cadena con el nombre y apellido concatenados
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}