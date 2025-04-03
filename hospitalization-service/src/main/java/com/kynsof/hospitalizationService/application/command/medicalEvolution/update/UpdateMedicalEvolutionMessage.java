package com.kynsof.hospitalizationService.application.command.medicalEvolution.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateMedicalEvolutionMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_MEDICAL_EVALUATION";

    public UpdateMedicalEvolutionMessage(UUID id) {
        this.id = id;
    }

}
