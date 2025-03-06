package com.kynsof.evaluation.application.command.evaluationPatient.update;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateEvaluationPatientRequest {
    private List<String> answers;
}
