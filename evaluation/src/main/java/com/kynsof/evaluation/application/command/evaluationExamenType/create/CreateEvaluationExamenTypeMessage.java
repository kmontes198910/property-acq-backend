package com.kynsof.evaluation.application.command.evaluationExamenType.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateEvaluationExamenTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_EVALUATION_EXAMEN_TYPE";

    public CreateEvaluationExamenTypeMessage(UUID id) {
        this.id = id;
    }

}
