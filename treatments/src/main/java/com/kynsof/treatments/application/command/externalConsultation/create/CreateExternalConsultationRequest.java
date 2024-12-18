package com.kynsof.treatments.application.command.externalConsultation.create;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateExternalConsultationRequest {

    private UUID patient;
    private UUID doctor;
    private UUID business;
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String observations;
    private List<ExamRequest> exams;
    private List<DiagnosisRequest> diagnosis;
    private List<TreatmentRequest> treatments;
    private List<OptometryExamRequest> optometryExams;
    private String medicalSpeciality;
}
