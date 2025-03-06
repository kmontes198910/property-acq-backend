package com.kynsof.evaluation.application.command.evaluationPatient.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEvaluationPatientRequest {
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String observation;
}
