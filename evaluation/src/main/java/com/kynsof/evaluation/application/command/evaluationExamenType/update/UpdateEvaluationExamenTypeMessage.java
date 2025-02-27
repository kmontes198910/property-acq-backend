package com.kynsof.evaluation.application.command.evaluationExamenType.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateEvaluationExamenTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_EVALUATION_EXAMEN_TYPE";

    public UpdateEvaluationExamenTypeMessage(UUID id) {
        this.id = id;
    }

}
