package com.kynsoft.medicaltest.infrastructure.entities;

import com.kynsoft.medicaltest.domain.dto.LabTestRequestDto;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lab_test_requests")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabTestRequestEntity {

    @Id
    private UUID id;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @Column(name = "doctor_id", nullable = false)
    private UUID doctorId;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;

    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LabTestItemRequestEntity> examinations = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private boolean isActive = true;

    public LabTestRequestEntity(LabTestRequestDto dto) {
        this.id = dto.getId();
        this.patientId = dto.getPatientId();
        this.doctorId = dto.getDoctorId();
        this.status = dto.getStatus();
        this.observations = dto.getObservations();
        this.businessId = dto.getBusinessId();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.isActive = dto.isActive();
        this.creationDate = dto.getCreationDate();

        // Procesar listas
        if (dto.getExaminations() != null) {
            dto.getExaminations().forEach(eDto -> {
                LabTestItemRequestEntity lt = new LabTestItemRequestEntity(eDto);
                lt.setOrder(this);
                this.examinations.add(lt);
            });
        }
    }

    public LabTestRequestDto toAggregate() {
        return LabTestRequestDto.builder()
                .id(id)
                .patientId(patientId)
                .doctorId(doctorId)
                .status(status)
                .observations(observations)
                .businessId(businessId)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .updatedBy(updatedBy)
                .createdBy(createdBy)
                .isActive(isActive)
                .creationDate(creationDate)
                .build();
                
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
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
