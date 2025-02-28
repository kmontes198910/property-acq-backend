package com.kynsof.evaluation.application.command.evaluationExamenType.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateEvaluationExamenTypeCommand implements ICommand {

    private UUID id;
    private String name;

    public UpdateEvaluationExamenTypeCommand(String name, UUID id) {
        this.id = id;
        this.name = name;
    }

    public static UpdateEvaluationExamenTypeCommand fromRequest(UpdateEvaluationExamenTypeRequest request, UUID id) {
        return new UpdateEvaluationExamenTypeCommand(request.getName(), id);
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateEvaluationExamenTypeMessage(id);
    }
}
