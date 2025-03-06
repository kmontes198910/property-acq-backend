package com.kynsof.evaluation.application.command.evaluation.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEvaluationRequest {
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String observation;
}
