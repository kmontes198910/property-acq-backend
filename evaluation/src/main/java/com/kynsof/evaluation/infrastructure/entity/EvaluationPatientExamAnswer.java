package com.kynsof.evaluation.infrastructure.entity;

import com.kynsof.evaluation.domain.dto.EvaluationPatientExamAnswerDto;
import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "evaluation_patient_exam_answers")
public class EvaluationPatientExamAnswer {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "patient_exam_id", nullable = false)
    private EvaluationPatientExam patientExam;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private EvaluationQuestion question;

    private boolean correct;
    private int scoreObtained;



    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public EvaluationPatientExamAnswer(EvaluationPatientExam patientExam, EvaluationQuestion question, boolean correct,
                                       int scoreObtained) {
        this.id = UUID.randomUUID();
        this.patientExam = patientExam;
        this.question = question;
        this.correct = correct;
        this.scoreObtained = scoreObtained;
    }

    public EvaluationPatientExamAnswer(EvaluationPatientExamAnswerDto dto) {
        this.id = dto.getId();
        this.patientExam = dto.getPatientExam() != null ? new EvaluationPatientExam(dto.getPatientExam()) : null;
        this.question = dto.getQuestion() != null ? new EvaluationQuestion(dto.getQuestion()) : null;
        this.correct = dto.isCorrect();
        this.scoreObtained = dto.getScoreObtained();
    }

    public EvaluationPatientExamAnswerDto toAggregate() {
        return new EvaluationPatientExamAnswerDto(
                this.id,
                this.patientExam != null ? this.patientExam.toAggregate() : null,
                this.question != null ? this.question.toAggregate() : null,
                this.correct,
                this.scoreObtained,
                this.createdAt,
                this.updatedAt
        );
    }

}
