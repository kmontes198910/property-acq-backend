package com.kynsoft.settings.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
    private UUID id;

    @Column(name = "recovery_bed_id")
    private UUID recoveryBedEntityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recovery_bed_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RecoveryBedEntity recoveryBed;

    @Column(name = "operating_room_id")
    private UUID operatingRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operating_room_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OperatingRoomEntity operatingRoom;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Patient patient;

    @Column(name = "doctor_id", nullable = false)
    private UUID doctorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Doctor doctor;

    @Column(name = "specialty_id", nullable = false)
    private UUID specialtyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SpecialtyEntity specialty;

    @Column(name = "surgery_type", nullable = false)
    private String surgeryType;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDate scheduledDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "ending_time")
    private LocalTime endingTime;

    @Column(name = "requires_hospitalization")
    private Boolean requiresHospitalization;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Business business;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "surgery")
    private List<BedAssignmentEntity> bedAssignments;
}
