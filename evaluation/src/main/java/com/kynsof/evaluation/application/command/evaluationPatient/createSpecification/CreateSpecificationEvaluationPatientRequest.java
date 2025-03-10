package com.kynsof.evaluation.application.command.evaluationPatient.createSpecification;

import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateSpecificationEvaluationPatientRequest {
    private UUID patient;
    private UUID evaluationId;
    private List<CodeAnswerRequest> examenListCode;
    private EvaluationExamenType examenType;
}
