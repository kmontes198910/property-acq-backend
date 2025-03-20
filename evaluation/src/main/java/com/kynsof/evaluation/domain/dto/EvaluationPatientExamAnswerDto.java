package com.kynsof.evaluation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class EvaluationPatientExamAnswerDto {
    private UUID id;
    private EvaluationPatientExamDto patientExam;
    private EvaluationQuestionDto question;
    private boolean correct;
    private int scoreObtained;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
