package com.kynsof.evaluation.application.command.evaluation.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateEvaluationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_EVALUATION";

    public UpdateEvaluationMessage(UUID id) {
        this.id = id;
    }

}
