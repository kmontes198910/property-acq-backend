package com.kynsof.evaluation.application.query.getExamByEvaluationIdAndType.getById;

import com.kynsof.evaluation.application.command.evaluationPatient.createSpecification.CodeAnswerRequest;
import com.kynsof.evaluation.domain.dto.EvaluationDto;
import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import com.kynsof.evaluation.infrastructure.entity.EvaluationPatientExam;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationPatientExamResponse  implements IResponse {
    private UUID id;
    private EvaluationDto evaluation;
    private LocalDate examDate;
    private int totalScore;
    private EvaluationExamenType examenType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> questionsCode;
    private List<CodeAnswerRequest> responseCodes;

    public EvaluationPatientExamResponse(EvaluationPatientExam examByEvaluationIdAndType) {
        this.id = examByEvaluationIdAndType.getId();
        this.examDate = examByEvaluationIdAndType.getExamDate();
        this.totalScore = examByEvaluationIdAndType.getTotalScore();
        this.examenType = examByEvaluationIdAndType.getExamenType();
        this.createdAt = examByEvaluationIdAndType.getCreatedAt();
        this.updatedAt = examByEvaluationIdAndType.getUpdatedAt();
        this.questionsCode = examByEvaluationIdAndType.getAnswers().stream().map(evaluationPatientExamAnswer -> {
            return evaluationPatientExamAnswer.getQuestion().getCode();
        }).toList();

        this.responseCodes = examByEvaluationIdAndType.getAnswers().stream().map(evaluationPatientExamAnswer -> {
            return new CodeAnswerRequest( evaluationPatientExamAnswer.getQuestion().getCode(), evaluationPatientExamAnswer.getResponse());
        }).toList();
    }
}