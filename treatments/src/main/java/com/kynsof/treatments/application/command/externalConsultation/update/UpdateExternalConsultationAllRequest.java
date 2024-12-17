package com.kynsof.treatments.application.command.externalConsultation.update;

import com.kynsof.treatments.application.command.externalConsultation.create.OptometryExamRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateExternalConsultationAllRequest {
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String observations;
    private String medicalSpeciality;
    private UpdateExamOrderAllRequest examOrder;
    private List<UpdateDiagnosisAllRequest> diagnosis;
    private List<UpdateTreatmentAllRequest> treatments;
    private List<UpdateOptometryExamRequest> optometryExams;
}
