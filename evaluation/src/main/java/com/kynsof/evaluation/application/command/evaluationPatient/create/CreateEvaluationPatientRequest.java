package com.kynsof.evaluation.application.command.evaluationPatient.create;

import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateEvaluationPatientRequest {
    private UUID patient;
    private UUID evaluationId;
    private List<String> examenListCode;
    private EvaluationExamenType examenType;
}
