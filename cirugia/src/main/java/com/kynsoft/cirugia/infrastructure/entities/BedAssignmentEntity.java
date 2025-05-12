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
    
    @Column(name = "release_date")
    private LocalDateTime releaseDate;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "assigned_by")
    private UUID assignedBy;


    @Column(name = "business_id", nullable = false)
    private UUID businessId;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private UUID createdBy;
    
    @Column(name = "updated_by")
    private UUID updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bed_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RecoveryBedEntity recoveryBed;

}