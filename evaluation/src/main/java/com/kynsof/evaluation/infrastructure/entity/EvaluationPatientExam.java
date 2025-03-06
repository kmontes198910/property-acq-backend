package com.kynsof.evaluation.infrastructure.entity;

import com.kynsof.evaluation.domain.dto.EvaluationPatientExamDto;
import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "evaluation_patient_exams")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationPatientExam {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;
    private LocalDate examDate;
    private int totalScore;

    @OneToMany(mappedBy = "patientExam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationPatientExamAnswer> answers;

    @Enumerated(EnumType.STRING)
    private EvaluationExamenType examenType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public EvaluationPatientExam(EvaluationPatientExamDto dto) {
        this.id = dto.getId();
        this.evaluation = dto.getEvaluation() != null ? new Evaluation(dto.getEvaluation()) : null;
        this.examDate = dto.getExamDate();
        this.totalScore = dto.getTotalScore();
        this.examenType = dto.getExamenType();
    }

    public EvaluationPatientExamDto toAggregate() {
        return new EvaluationPatientExamDto(
                this.id,
                this.evaluation != null ? this.evaluation.toAggregate() : null,
                this.examDate,
                this.totalScore,
                examenType,
                this.createdAt,
                this.updatedAt
        );
    }

}