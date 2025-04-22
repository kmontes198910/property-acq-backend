package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.ConstructionTypeDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad que representa los diferentes tipos de construcción
 */
@Entity
@Table(name = "construction_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstructionType {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "code", length = 50)
    private String code;
    
    @Column(name = "is_specialized")
    private Boolean isSpecialized;
    
    @Column(name = "specialization_area")
    private String specializationArea;
    
    @Column(name = "requires_license")
    private Boolean requiresLicense;

    @Column(name = "is_active")
    private Boolean isActive;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Método para convertir la entidad a DTO
     * @return ConstructionTypeDto con los datos de esta entidad
     */
    public ConstructionTypeDto toAggregate() {
        return ConstructionTypeDto.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .code(this.code)
                .isSpecialized(this.isSpecialized)
                .specializationArea(this.specializationArea)
                .requiresLicense(this.requiresLicense)
                .isActive(this.isActive)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
    
    /**
     * Constructor a partir de un DTO
     * @param dto ConstructionTypeDto con los datos a utilizar
     */
    public ConstructionType(ConstructionTypeDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.code = dto.getCode();
        this.isSpecialized = dto.getIsSpecialized();
        this.specializationArea = dto.getSpecializationArea();
        this.requiresLicense = dto.getRequiresLicense();
        this.isActive = dto.getIsActive();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
    }
}