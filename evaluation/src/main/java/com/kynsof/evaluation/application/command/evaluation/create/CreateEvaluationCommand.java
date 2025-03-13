package com.kynsof.evaluation.application.command.evaluation.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateEvaluationCommand implements ICommand {

    private UUID id;
    private UUID patient;
    private UUID doctor;
    private UUID business;
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String observation;

    public CreateEvaluationCommand(UUID patient, String consultationReason, String medicalHistory, String physicalExam, String observation, UUID doctor, UUID business) {
        this.id = UUID.randomUUID();
        this.patient = patient;
        this.consultationReason = consultationReason;
        this.medicalHistory = medicalHistory;
        this.physicalExam = physicalExam;
        this.observation = observation;
        this.doctor = doctor;
        this.business = business;
    }

    public static CreateEvaluationCommand fromRequest(CreateEvaluationRequest request) {
        return new CreateEvaluationCommand(
                request.getPatient(), 
                request.getConsultationReason(), 
                request.getMedicalHistory(), 
                request.getPhysicalExam(), 
                request.getObservation(),
                request.getDoctor(),
                request.getBusiness()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateEvaluationMessage(id);
    }
}
