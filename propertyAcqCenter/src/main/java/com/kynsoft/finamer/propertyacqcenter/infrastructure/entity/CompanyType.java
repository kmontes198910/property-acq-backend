package com.kynsoft.finamer.propertyacqcenter.infrastructure.entity;

import com.kynsoft.finamer.propertyacqcenter.domain.dto.CompanyTypeDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad que representa los tipos de empresas inmobiliarias
 */
@Entity
@Table(name = "company_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyType {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "examples")
    private String examples;
    
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
     * @return CompanyTypeDto con los datos de esta entidad
     */
    public CompanyTypeDto toAggregate() {
        return CompanyTypeDto.builder()
                .id(this.id)
                .name(this.name)
                .code(this.code)
                .description(this.description)
                .examples(this.examples)
                .isActive(this.isActive)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
    
    /**
     * Constructor a partir de un DTO
     * @param dto CompanyTypeDto con los datos a utilizar
     */
    public CompanyType(CompanyTypeDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.code = dto.getCode();
        this.description = dto.getDescription();
        this.examples = dto.getExamples();
        this.isActive = dto.getIsActive();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
    }
}