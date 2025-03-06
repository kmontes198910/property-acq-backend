package com.kynsof.evaluation.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class EvaluationQuestionDto {
    private UUID id;
    private String code;
    private String text;
    private int maxScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EvaluationQuestionDto(UUID id, String code, String text, int maxScore) {
        this.id = id;
        this.code = code;
        this.text = text;
        this.maxScore = maxScore;
    }

}
