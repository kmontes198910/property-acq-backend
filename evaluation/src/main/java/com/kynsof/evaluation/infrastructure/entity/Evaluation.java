package com.kynsof.evaluation.infrastructure.entity;

import com.kynsof.evaluation.domain.dto.EvaluationDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Evaluation {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patients patient;

    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String medicalSpeciality;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationPatientExam> evaluationPatientExams;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Evaluation(EvaluationDto dto) {
        this.id = dto.getId();
        this.patient = dto.getPatient() != null ? new Patients(dto.getPatient()) : null;
        this.consultationReason = dto.getConsultationReason();
        this.medicalHistory = dto.getMedicalHistory();
        this.physicalExam = dto.getPhysicalExam();
        this.medicalSpeciality = dto.getMedicalSpeciality();
    }

    public EvaluationDto toAggregate() {
        return new EvaluationDto(
                this.id,
                this.patient != null ? this.patient.toAggregate() : null,
                this.consultationReason,
                this.medicalHistory,
                this.physicalExam,
                this.medicalSpeciality,
                this.createdAt,
                this.updatedAt
        );
    }

}