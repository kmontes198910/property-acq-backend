package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "anesthesia")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnesthesiaEntity {
    
    @Id
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "surgery_id")
    private UUID surgeryId;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surgery_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SurgeryEntity surgery;
    
    @Column(name = "anesthesia_type")
    private String anesthesiaType;
    
    @Column(name = "mouth_opening")
    private String mouthOpening;
    
    @Column(name = "thyromental_distance")
    private String thyroMentalDistance;
    
    @Column(name = "neck_circumference")
    private String neckCircumference;
    
    @Column(name = "mallampati")
    private String mallampati;
    
    @Column(name = "cervical_mobility")
    private String cervicalMobility;
    
    @Column(name = "mandibular_protrusion")
    private String mandibularProtrusion;
    
    @Column(name = "difficult_intubation_history")
    private Boolean difficultIntubationHistory;
    
    @Column(name = "intubation_difficulties")
    private Boolean intubationDifficulties;
    
    @Column(name = "thorax_description")
    private String thoraxDescription;
    
    @Column(name = "heart_description")
    private String heartDescription;
    
    @Column(name = "lungs_description")
    private String lungsDescription;
    
    @Column(name = "abdomen_description")
    private String abdomenDescription;
    
    @Column(name = "extremities_description")
    private String extremitiesDescription;
    
    @Column(name = "central_nervous_system")
    private String centralNervousSystem;
    
    @Column(name = "asa_physical_status")
    private String asaPhysicalStatus;
    
    @Column(name = "operation_risks")
    private String operationRisks;
    
    @Column(name = "metabolic_equivalent")
    private String metabolicEquivalent;
    
    @Column(name = "last_intoxication_hours")
    private Integer lastIntoxicationHours;
    
    @Column(name = "anesthetics")
    private String anesthetics;
    
    @Column(name = "surgical_drugs")
    private String surgicalDrugs;
    
    @Column(name = "allergies")
    private String allergies;
    
    @Column(name = "transfusions")
    private String transfusions;
    
    @Column(name = "habits")
    private String habits;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private UUID createdBy;
    
    @Column(name = "updated_by")
    private UUID updatedBy;
}