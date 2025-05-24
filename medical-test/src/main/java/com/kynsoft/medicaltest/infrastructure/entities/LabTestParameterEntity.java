package com.kynsoft.medicaltest.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad JPA que representa un parámetro específico de un examen de laboratorio en la base de datos
 */
@Entity
@Table(name = "lab_test_parameters")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabTestParameterEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_test_id", nullable = false)
    private LabTestEntity labTest;

    @Column(name = "lab_test_id", insertable = false, updatable = false)
    private UUID labTestId;
    
    @Column(name = "code", nullable = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "unit", nullable = false)
    private String unit;
    
    @Column(name = "reference_range_min", precision = 10, scale = 4)
    private BigDecimal referenceRangeMin;
    
    @Column(name = "reference_range_max", precision = 10, scale = 4)
    private BigDecimal referenceRangeMax;
    
    @Column(name = "gender_specific", nullable = false)
    private Boolean genderSpecific;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private UUID createdBy;
    
    @Column(name = "updated_by")
    private UUID updatedBy;
}
