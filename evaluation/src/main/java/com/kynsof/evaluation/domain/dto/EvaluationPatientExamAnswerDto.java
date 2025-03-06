package com.kynsof.evaluation.domain.dto;

import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;

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
