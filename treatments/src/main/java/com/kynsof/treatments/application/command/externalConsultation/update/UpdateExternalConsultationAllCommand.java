package com.kynsof.treatments.application.command.externalConsultation.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsof.treatments.application.command.externalConsultation.create.OptometryExamRequest;
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
    private final List<OptometryExamRequest> optometryExamRequests;

    public UpdateExternalConsultationAllCommand(UUID id, String consultationReason,
                                                String medicalHistory, String physicalExam, String observations,
                                                List<OptometryExamRequest> optometryExamRequests) {

        this.id = id;
        this.consultationReason = consultationReason;
        this.medicalHistory = medicalHistory;
        this.physicalExam = physicalExam;
        this.observations = observations;
        this.optometryExamRequests = optometryExamRequests;
    }

    public static UpdateExternalConsultationAllCommand fromRequest(UUID id,UpdateExternalConsultationAllRequest request) {
        return new UpdateExternalConsultationAllCommand(id, request.getConsultationReason(), request.getMedicalHistory(), request.getPhysicalExam(),
                request.getObservations(),request.getOptometryExams());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateExternalConsultationAllMessage(id);
    }
}
