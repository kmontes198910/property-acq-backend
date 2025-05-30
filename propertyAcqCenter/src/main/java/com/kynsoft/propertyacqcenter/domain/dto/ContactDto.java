package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import com.kynsoft.propertyacqcenter.domain.enums.DepartmentType;
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
    private DepartmentType department;
    private ContactType category;
    private String notes;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LegalEntityDto legalEntity;
    private String personalEmail;
    private SubCategoryDto subCategory;

    public ContactDto(UUID id, String firstName, String lastName, String email, String phoneNumber, String position, DepartmentType department, ContactType category, String notes, Boolean isActive, LegalEntityDto legalEntity, String personalEmail, SubCategoryDto subCategory) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.department = department;
        this.category = category;
        this.notes = notes;
        this.isActive = isActive;
        this.legalEntity = legalEntity;
        this.personalEmail = personalEmail;
        this.subCategory = subCategory;
    }

    /**
     * Obtiene el nombre completo del contacto
     * @return Cadena con el nombre y apellido concatenados
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}