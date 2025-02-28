package com.kynsof.evaluation.application.command.evaluationExamenType.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteEvaluationExamenTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_EVALUATION_EXAMEN_TYPE";

    public DeleteEvaluationExamenTypeMessage(UUID id) {
        this.id = id;
    }

}
