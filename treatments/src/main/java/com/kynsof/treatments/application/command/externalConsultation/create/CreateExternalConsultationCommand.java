package com.kynsof.treatments.application.command.externalConsultation.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateExternalConsultationCommand implements ICommand {

    private UUID id;
    private UUID patientId;
    private UUID doctorId;
    private UUID businessId;
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String observations;
    private List<ExamRequest> exams;
    private final List<DiagnosisRequest> diagnosis;
    private final List<TreatmentRequest> treatments;
    private final List<OptometryExamRequest> optometryExams;
    private final String medicalSpeciality;
    private final String odontogramJson;

    public CreateExternalConsultationCommand(UUID patientId, UUID doctorId, String consultationReason,
                                             String medicalHistory, String physicalExam, String observations,
                                             List<ExamRequest> exams, List<DiagnosisRequest> diagnosis,
                                             List<TreatmentRequest> treatments, UUID businessId, List<OptometryExamRequest> optometryExams, String medicalSpeciality, String odontogramJson) {

        this.patientId = patientId;
        this.doctorId = doctorId;
        this.businessId = businessId;
        this.consultationReason = consultationReason;
        this.medicalHistory = medicalHistory;
        this.physicalExam = physicalExam;
        this.observations = observations;
        this.exams = exams;
        this.diagnosis = diagnosis;
        this.treatments = treatments;
        this.optometryExams = optometryExams;
        this.medicalSpeciality = medicalSpeciality;
        this.odontogramJson = odontogramJson;
    }

    public static CreateExternalConsultationCommand fromRequest(CreateExternalConsultationRequest request) {
        return new CreateExternalConsultationCommand(request.getPatient(), request.getDoctor(),
                request.getConsultationReason(), request.getMedicalHistory(), request.getPhysicalExam(),
                request.getObservations(), request.getExams(),request.getDiagnosis() ,request.getTreatments(),
                request.getBusiness(),
                request.getOptometryExams(),
                request.getMedicalSpeciality(),
                request.getOdontogramJson());
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateExternalConsultationMessage(id);
    }
}
