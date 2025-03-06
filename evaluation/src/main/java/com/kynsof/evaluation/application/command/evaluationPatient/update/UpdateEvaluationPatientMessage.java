package com.kynsof.evaluation.application.command.evaluationPatient.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateEvaluationPatientMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_EVALUATION";

    public UpdateEvaluationPatientMessage(UUID id) {
        this.id = id;
    }

}
