package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "surgeries")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SurgeryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;
    
    @Column(name = "doctor_id", nullable = false)
    private UUID doctorId;
    
    @Column(name = "specialty_id", nullable = false)
    private UUID specialtyId;
    
    @Column(name = "surgery_type", nullable = false)
    private String surgeryType;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "scheduled_date", nullable = false)
    private LocalDateTime scheduledDate;
    
    @Column(name = "performed_date")
    private LocalDateTime performedDate;
    
    @Column(name = "estimated_duration_minutes")
    private Integer estimatedDurationMinutes;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "preoperative_notes", columnDefinition = "TEXT")
    private String preoperativeNotes;
    
    @Column(name = "postoperative_notes", columnDefinition = "TEXT")
    private String postoperativeNotes;
    
    @Column(name = "complexity_level")
    private String complexityLevel;
    
    @Column(name = "room_id")
    private UUID roomId;
    
    @Column(name = "requires_hospitalization")
    private Boolean requiresHospitalization;
    
    @Column(name = "admission_reason", columnDefinition = "TEXT")
    private String admissionReason;
    
    @Column(name = "current_illness_history", columnDefinition = "TEXT")
    private String currentIllnessHistory;
    
    @Column(name = "physical_examination", columnDefinition = "TEXT")
    private String physicalExamination;
    
    @Column(name = "operating_room_entry_date")
    private LocalDateTime operatingRoomEntryDate;

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

}