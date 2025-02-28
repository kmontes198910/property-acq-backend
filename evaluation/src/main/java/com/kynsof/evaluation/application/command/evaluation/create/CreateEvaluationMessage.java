package com.kynsof.evaluation.application.command.evaluation.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateEvaluationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_EVALUATION";

    public CreateEvaluationMessage(UUID id) {
        this.id = id;
    }

}
