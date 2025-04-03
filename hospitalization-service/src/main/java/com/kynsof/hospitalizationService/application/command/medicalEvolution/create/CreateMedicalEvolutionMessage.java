package com.kynsof.hospitalizationService.application.command.medicalEvolution.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateMedicalEvolutionMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_MEDICAL_EVALUATION";

    public CreateMedicalEvolutionMessage(UUID id) {
        this.id = id;
    }

}
