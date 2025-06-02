package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad JPA que representa la evolución de un paciente después de una cirugía en la base de datos.
 */
@Data
@Entity
@Table(name = "evolutions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvolutionEntity {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    
    @Column(name = "surgery_id", nullable = false)
    private UUID surgeryId;
    
    @Column(name = "therapeutic_fluids", columnDefinition = "TEXT")
    private String therapeuticFluids;
    
    @Column(name = "prescription_fluids", columnDefinition = "TEXT")
    private String prescriptionFluids;
    
    @Column(name = "general_care", columnDefinition = "TEXT")
    private String generalCare;
    
    @Column(name = "monitoring", columnDefinition = "TEXT")
    private String monitoring;
    
    @Column(name = "diet", columnDefinition = "TEXT")
    private String diet;
    
    @Column(name = "analytics", columnDefinition = "TEXT")
    private String analytics;
    
    @Column(name = "others", columnDefinition = "TEXT")
    private String others;
    
    @Column(name = "process")
    private String process;
    
    @Column(name = "evolution_date", nullable = false)
    private LocalDateTime evolutionDate;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private UUID createdBy;
    
    @Column(name = "updated_by")
    private UUID updatedBy;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surgery_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SurgeryEntity surgery;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (evolutionDate == null) {
            evolutionDate = LocalDateTime.now();
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