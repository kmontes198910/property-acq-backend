package com.kynsof.hospitalizationService.application.command.medicalEvolution.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateMedicalEvolutionCommand implements ICommand {

    private UUID id;
    private UUID hospitalization;
    private String recordDate;
    private String observations;
    private String diagnosis;

    public CreateMedicalEvolutionCommand(UUID hospitalization, String recordDate, String observations, String diagnosis) {
        this.id = UUID.randomUUID();
        this.hospitalization = hospitalization;
        this.recordDate = recordDate;
        this.observations = observations;
        this.diagnosis = diagnosis;
    }

    public static CreateMedicalEvolutionCommand fromRequest(CreateMedicalEvolutionRequest request) {
        return new CreateMedicalEvolutionCommand(
                request.getHospitalization(),
                request.getRecordDate(),
                request.getObservations(),
                request.getDiagnosis()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateMedicalEvolutionMessage(id);
    }
}
