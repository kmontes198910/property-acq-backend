package com.kynsoft.medicaltest.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "examinations")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExaminationEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private ExaminationOrderEntity order;
    
    @Column(name = "code", nullable = false)
    private String code;
    
    @Column(name = "examination_type", nullable = false)
    private String examinationType;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "results", columnDefinition = "TEXT")
    private String results;
    
    @Column(name = "completion_date")
    private LocalDateTime completionDate;
    
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
