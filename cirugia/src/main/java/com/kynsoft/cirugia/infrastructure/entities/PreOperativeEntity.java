package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pre_operative")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PreOperativeEntity {

    @Id
    private UUID id;

    @Column(name = "surgery_id", nullable = false, unique = true)
    private UUID surgeryId;

    @Column(name = "admission_reason")
    private String admissionReason;

    @Column(name = "current_disease_history", columnDefinition = "TEXT")
    private String currentDiseaseHistory;

    @Column(name = "physical_examination", columnDefinition = "TEXT")
    private String physicalExamination;

    @Column(name = "surgery_room_date")
    private LocalDateTime surgeryRoomDate;

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
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surgery_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SurgeryEntity surgery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Business business;
}