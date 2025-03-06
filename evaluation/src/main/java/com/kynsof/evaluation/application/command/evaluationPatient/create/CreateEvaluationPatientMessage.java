package com.kynsof.evaluation.application.command.evaluationPatient.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateEvaluationPatientMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_EVALUATION";

    public CreateEvaluationPatientMessage(UUID id) {
        this.id = id;
    }

}
