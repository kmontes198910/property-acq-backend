package com.kynsoft.medicaltest.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad JPA que representa un resultado de un parámetro de examen de laboratorio en la base de datos
 */
@Entity
@Table(name = "lab_test_results")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabTestResultEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_test_item_id", nullable = false)
    private LabTestItemRequestEntity labTestItem;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_test_parameter_id", nullable = false)
    private LabTestParameterEntity labTestParameter;
    
    @Column(name = "value", nullable = false, columnDefinition = "TEXT")
    private String value;
    
    @Column(name = "unit")
    private String unit;
    
    @Column(name = "flag")
    private String flag;//(normal, low, high, critical)
    
    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private UUID createdBy;
    
    @Column(name = "updated_by")
    private UUID updatedBy;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
