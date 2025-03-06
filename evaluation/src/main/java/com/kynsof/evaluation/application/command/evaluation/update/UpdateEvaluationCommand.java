package com.kynsof.evaluation.application.command.evaluation.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateEvaluationCommand implements ICommand {

    private UUID id;
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String observation;

    public UpdateEvaluationCommand(UUID id, String consultationReason, String medicalHistory, String physicalExam,
                                   String medicalSpeciality) {
        this.id = id;
        this.consultationReason = consultationReason;
        this.medicalHistory = medicalHistory;
        this.physicalExam = physicalExam;
        this.observation = medicalSpeciality;

    }

    public static UpdateEvaluationCommand fromRequest(UpdateEvaluationRequest request, UUID evaluationId) {
        return new UpdateEvaluationCommand(
                evaluationId,
                request.getConsultationReason(),
                request.getMedicalHistory(),
                request.getPhysicalExam(),
                request.getObservation()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateEvaluationMessage(id);
    }
}
