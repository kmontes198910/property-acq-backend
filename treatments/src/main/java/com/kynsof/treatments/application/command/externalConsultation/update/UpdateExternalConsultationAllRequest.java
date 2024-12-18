package com.kynsof.treatments.application.command.externalConsultation.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateExternalConsultationAllRequest {
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String observations;
}
