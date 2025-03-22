package com.kynsof.evaluation.application.command.evaluationPatient.updateSpecification;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateSpecificationEvaluationPatientRequest {
    private List<CodeAnswerUpdateRequest> examenListCode;

}
