package com.kynsof.evaluation.infrastructure.entity;

import com.kynsof.evaluation.domain.dto.EvaluationQuestionDto;
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
@Table(name = "evaluation_questions")
public class EvaluationQuestion {
    @Id
    private UUID id;

    private String code;
    private String text;
    private int maxScore;


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
    }

    public EvaluationQuestionDto toAggregate() {
        return new EvaluationQuestionDto(
                this.id,
                this.code,
                this.text,
                this.maxScore,
                this.createdAt,
                this.updatedAt
        );
    }

}
