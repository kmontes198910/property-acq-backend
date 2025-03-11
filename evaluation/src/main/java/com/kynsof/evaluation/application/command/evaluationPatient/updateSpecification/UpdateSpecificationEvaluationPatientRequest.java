package com.kynsof.evaluation.application.command.evaluationPatient.updateSpecification;

import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateSpecificationEvaluationPatientRequest {
    private List<CodeAnswerUpdateRequest> examenListCode;

}
