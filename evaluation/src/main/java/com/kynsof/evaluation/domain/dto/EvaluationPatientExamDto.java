package com.kynsof.evaluation.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class EvaluationPatientExamDto {
    private UUID id;
    private EvaluationDto evaluation;
    private LocalDate examDate;
    private int totalScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EvaluationPatientExamDto(UUID id, EvaluationDto evaluation,  LocalDate examDate, int totalScore) {
        this.id = id;
        this.evaluation = evaluation;
        this.examDate = examDate;
        this.totalScore = totalScore;
    }

}