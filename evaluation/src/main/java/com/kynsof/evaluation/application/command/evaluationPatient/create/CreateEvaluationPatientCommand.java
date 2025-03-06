package com.kynsof.evaluation.application.command.evaluationPatient.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateEvaluationPatientCommand implements ICommand {

    private UUID id;
    private UUID patient;
    private UUID doctor;
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String medicalSpeciality;

    public CreateEvaluationPatientCommand(UUID patient, String consultationReason, String medicalHistory, String physicalExam, String medicalSpeciality, UUID doctor) {
        this.id = UUID.randomUUID();
        this.patient = patient;
        this.consultationReason = consultationReason;
        this.medicalHistory = medicalHistory;
        this.physicalExam = physicalExam;
        this.medicalSpeciality = medicalSpeciality;
        this.doctor = doctor;
    }

    public static CreateEvaluationPatientCommand fromRequest(CreateEvaluationPatientRequest request) {
//        return new CreateEvaluationPatientCommand(
//                request.getPatient(),
//                request.getConsultationReason(),
//                request.getMedicalHistory(),
//                request.getPhysicalExam(),
//                request.getObservation(),
//                request.getDoctor()
//        );

        return null;
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateEvaluationPatientMessage(id);
    }
}
