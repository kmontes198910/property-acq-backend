package com.kynsof.hospitalizationService.application.command.medicalEvolution.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateMedicalEvolutionCommand implements ICommand {

    private UUID id;
    private UUID hospitalization;
    private String recordDate;
    private String observations;
    private String diagnosis;

    public UpdateMedicalEvolutionCommand(UUID id, UUID hospitalization, String recordDate, String observations, String diagnosis) {
        this.id = id;
        this.hospitalization = hospitalization;
        this.recordDate = recordDate;
        this.observations = observations;
        this.diagnosis = diagnosis;
    }

    public static UpdateMedicalEvolutionCommand fromRequest(UpdateMedicalEvolutionRequest request, UUID id) {
        return new UpdateMedicalEvolutionCommand(
                id,
                request.getHospitalization(),
                request.getRecordDate(),
                request.getObservations(),
                request.getDiagnosis()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateMedicalEvolutionMessage(id);
    }
}
