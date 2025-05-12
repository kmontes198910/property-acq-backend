package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing the admission data of a patient for surgery
 */
@Entity
@Table(name = "admissions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdmisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "room_id")
    private UUID roomId;

    @Column(name = "bed")
    private UUID bedId;

    @Column(name = "observations", length = 1000)
    private String observations;

    @Column(name = "surgery_id")
    private UUID surgeryId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surgery_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SurgeryEntity surgery;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
