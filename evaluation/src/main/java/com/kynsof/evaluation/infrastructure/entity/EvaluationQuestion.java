package com.kynsof.evaluation.infrastructure.entity;

import com.kynsof.evaluation.domain.dto.EvaluationQuestionDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class EvaluationQuestion {
    @Id
    private UUID id;

    private String code;
    private String text;
    private int maxScore;

    @ManyToOne
    @JoinColumn(name = "exam_type_id", nullable = false)
    private EvaluationExamenType examType;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public EvaluationQuestion(EvaluationQuestionDto dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.text = dto.getText();
        this.maxScore = dto.getMaxScore();
        this.examType = dto.getExamType() != null ? new EvaluationExamenType(dto.getExamType()) : null;
    }

    public EvaluationQuestionDto toAggregate() {
        return new EvaluationQuestionDto(
                this.id,
                this.code,
                this.text,
                this.maxScore,
                this.examType != null ? this.examType.toAggregate() : null,
                this.createdAt,
                this.updatedAt
        );
    }

}
