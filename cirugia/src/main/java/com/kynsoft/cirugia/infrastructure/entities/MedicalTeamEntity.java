package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "medical_teams")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalTeamEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "surgery_id")
    private UUID surgeryId;

    @Column(name = "member_id")
    private UUID memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_last_name")
    private String memberLastName;

    @Column(name = "specialty_name")
    private String specialtyName;

    @Column(name = "specialty_code")
    private String specialtyCode;
    
    @Column(name = "speciality_type")
    private String specialityType;

    @Column(name = "role")
    private String role;

    @Column(name = "created_at")
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
}