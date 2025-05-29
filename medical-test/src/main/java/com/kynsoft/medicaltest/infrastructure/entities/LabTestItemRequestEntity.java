package com.kynsoft.medicaltest.infrastructure.entities;

import com.kynsoft.medicaltest.domain.dto.LabTestItemRequestDto;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "lab_test_items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabTestItemRequestEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private LabTestRequestEntity order;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "examination_type", nullable = false)
    private String examinationType;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;

    @OneToMany(mappedBy = "labTestItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LabTestResultEntity> testResults = new ArrayList<>();

    public LabTestItemRequestEntity(LabTestItemRequestDto dto) {
        this.id = dto.getId();
        this.order = dto.getOrder() != null ? new LabTestRequestEntity(dto.getOrder()) : null;
        this.code = dto.getCode();
        this.examinationType = dto.getExaminationType();
        this.status = dto.getStatus();
        this.completionDate = dto.getCompletionDate();
        this.observations = dto.getObservations();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        //this.testResults = testResults;
    }

    public LabTestItemRequestDto toAggregate() {
        return LabTestItemRequestDto.builder()
                .id(id)
                .code(code)
                .completionDate(completionDate)
                .examinationType(examinationType)
                .observations(observations)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .build();
    }

    public LabTestItemRequestDto toAggregateSimple() {
        return LabTestItemRequestDto.builder()
                .id(id)
                .code(code)
                .order(order.toAggregateSimple())
                .completionDate(completionDate)
                .examinationType(examinationType)
                .observations(observations)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .createdBy(createdBy)
                .updatedBy(updatedBy)
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

    /**
     * Método helper para agregar un resultado de test al examen
     *
     * @param result El resultado a agregar
     */
    public void addTestResult(LabTestResultEntity result) {
        testResults.add(result);
        result.setLabTestItem(this);
    }

    /**
     * Método helper para eliminar un resultado de test del examen
     *
     * @param result El resultado a eliminar
     */
    public void removeTestResult(LabTestResultEntity result) {
        testResults.remove(result);
        result.setLabTestItem(null);
    }
}
