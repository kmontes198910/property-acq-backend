package com.kynsoft.cirugia.infrastructure.entities;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "intra_operative")
public class IntraOperativeEntity {

    @Id
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "surgery_id")
    private UUID surgeryId;
    
    @Column(name = "date")
    private LocalDate date;
    
    @Column(name = "start_time")
    private LocalTime startTime;
    
    @Column(name = "end_time")
    private LocalTime endTime;
    
    @Column(name = "procedure_type")
    private String procedureType;
    
    @Column(name = "anesthesia_type")
    private String anesthesiaType;
    
    @Column(name = "projected_procedure")
    private String projectedProcedure;
    
    @Column(name = "performed_procedure")
    private String performedProcedure;
    
    @Column(name = "dieresis")
    private String dieresis;
    
    @Column(name = "exposition_exploration")
    private String expositionExploration;
    
    @Column(name = "surgical_findings")
    private String surgicalFindings;
    
    @Column(name = "blood_loss")
    private Integer bloodLoss;
    
    @Column(name = "approximate_blood")
    private Integer approximateBlood;
    
    @Column(name = "prosthetic_material")
    private Boolean prostheticMaterial;
    
    @Column(name = "surgical_procedure")
    private String surgicalProcedure;
    
    @Column(name = "description")
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surgery_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SurgeryEntity surgery;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private UUID createdBy;
    
    @Column(name = "updated_by")
    private UUID updatedBy;
}