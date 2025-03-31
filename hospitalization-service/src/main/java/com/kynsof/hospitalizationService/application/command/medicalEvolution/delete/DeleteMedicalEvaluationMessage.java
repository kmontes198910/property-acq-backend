package com.kynsof.hospitalizationService.application.command.medicalEvolution.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteMedicalEvaluationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_MEDICAL_EVALUATION";

    public DeleteMedicalEvaluationMessage(UUID id) {
        this.id = id;
    }

}
