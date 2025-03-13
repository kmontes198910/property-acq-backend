package com.kynsof.evaluation.application.command.evaluationPatient.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateEvaluationPatientMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_EVALUATION_PATIENT";

    public UpdateEvaluationPatientMessage(UUID id) {
        this.id = id;
    }

}
