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
    private UUID legalEntityId;
    private UUID businessId;
    
    /**
     * Obtiene el nombre completo del contacto
     * @return Cadena con el nombre y apellido concatenados
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}