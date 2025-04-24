package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstructionTypeDto {
    
    private UUID id;
    private String name;
    private String description;
    private String code;
    private Boolean isSpecialized;
    private String specializationArea;
    private Boolean requiresLicense;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ConstructionTypeDto(UUID id, String name, String description, String code, Boolean isSpecialized, String specializationArea, Boolean requiresLicense, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.isSpecialized = isSpecialized;
        this.specializationArea = specializationArea;
        this.requiresLicense = requiresLicense;
        this.isActive = isActive;
    }

}