package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO que representa los tipos de construcción
 */
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
}