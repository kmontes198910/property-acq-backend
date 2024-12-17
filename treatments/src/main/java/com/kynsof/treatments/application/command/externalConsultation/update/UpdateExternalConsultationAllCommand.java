package com.kynsof.treatments.application.command.externalConsultation.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateExternalConsultationAllCommand implements ICommand {

    private UUID id;
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String observations;
    private String medicalSpeciality;
    private final UpdateExamOrderAllRequest examOrder;
    private final List<UpdateDiagnosisAllRequest> diagnosis;
    private final List<UpdateTreatmentAllRequest> treatments;
    private final List<UpdateOptometryExamRequest> optometryExams;

    public UpdateExternalConsultationAllCommand(UUID id, String consultationReason,
                                                String medicalHistory, String physicalExam, String observations,
                                                UpdateExamOrderAllRequest examOrder, List<UpdateDiagnosisAllRequest> diagnosis,
                                                List<UpdateTreatmentAllRequest> treatments, String medicalSpeciality, List<UpdateOptometryExamRequest> optometryExams) {

        this.id = id;
        this.consultationReason = consultationReason;
        this.medicalHistory = medicalHistory;
        this.physicalExam = physicalExam;
        this.observations = observations;
        this.diagnosis = diagnosis;
        this.treatments = treatments;
        this.examOrder = examOrder;
        this.medicalSpeciality = medicalSpeciality;
        this.optometryExams = optometryExams;
    }

    public static UpdateExternalConsultationAllCommand fromRequest(UUID id,UpdateExternalConsultationAllRequest request) {
        return new UpdateExternalConsultationAllCommand(id, request.getConsultationReason(), request.getMedicalHistory(), request.getPhysicalExam(),
                request.getObservations(), request.getExamOrder(), request.getDiagnosis(), request.getTreatments(), request.getMedicalSpeciality(),
                request.getOptometryExams());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateExternalConsultationAllMessage(id);
    }
}
