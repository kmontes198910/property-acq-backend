package com.kynsof.evaluation.application.command.evaluationPatient.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateEvaluationPatientCommand implements ICommand {

    private UUID id;
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String observation;

    public UpdateEvaluationPatientCommand(UUID id, String consultationReason, String medicalHistory, String physicalExam,
                                          String medicalSpeciality) {
        this.id = id;
        this.consultationReason = consultationReason;
        this.medicalHistory = medicalHistory;
        this.physicalExam = physicalExam;
        this.observation = medicalSpeciality;

    }

    public static UpdateEvaluationPatientCommand fromRequest(UpdateEvaluationPatientRequest request, UUID evaluationId) {
        return new UpdateEvaluationPatientCommand(
                evaluationId,
                request.getConsultationReason(),
                request.getMedicalHistory(),
                request.getPhysicalExam(),
                request.getObservation()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateEvaluationPatientMessage(id);
    }
}
