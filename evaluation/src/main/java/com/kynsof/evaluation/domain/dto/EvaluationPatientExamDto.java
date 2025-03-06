package com.kynsof.evaluation.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationPatientExamDto {
    private UUID id;
    private EvaluationDto evaluation;
    private LocalDate examDate;
    private int totalScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EvaluationPatientExamDto(EvaluationDto evaluation,  LocalDate examDate, int totalScore) {
        this.evaluation = evaluation;
        this.examDate = examDate;
        this.totalScore = totalScore;
    }

}