package com.kynsof.evaluation.application.command.evaluation.create;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEvaluationRequest {

    private UUID patient;
    private UUID doctor;
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String observations;
}
