package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bed_assignments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BedAssignmentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;
    
    @Column(name = "surgery_id", nullable = false)
    private UUID surgeryId;
    
    @Column(name = "bed_id", nullable = false)
    private UUID bedId;
    
    @Column(name = "assignment_date", nullable = false)
    private LocalDateTime assignmentDate;
    
    @Column(name = "planned_release_date")
    private LocalDateTime plannedReleaseDate;
    
    @Column(name = "actual_release_date")
    private LocalDateTime actualReleaseDate;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "medical_notes", columnDefinition = "TEXT")
    private String medicalNotes;
    
    @Column(name = "vital_signs", columnDefinition = "TEXT")
    private String vitalSigns;
    
    @Column(name = "care_instructions", columnDefinition = "TEXT")
    private String careInstructions;
    
    @Column(name = "assigned_by")
    private UUID assignedBy;
    
    @Column(name = "released_by")
    private UUID releasedBy;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private UUID createdBy;
    
    @Column(name = "updated_by")
    private UUID updatedBy;
    
    @Column(name = "business_id", nullable = false)
    private UUID businessId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bed_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RecoveryBedEntity recoveryBed;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surgery_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SurgeryEntity surgery;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Business business;
}