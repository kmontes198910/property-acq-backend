package com.kynsof.evaluation.application.command.evaluationExamenType.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateEvaluationExamenTypeCommand implements ICommand {
    private UUID id;
    private String name;

    public CreateEvaluationExamenTypeCommand(String name){
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public static CreateEvaluationExamenTypeCommand fromRequest(CreateEvaluationExamenTypeRequest request) {
        return new CreateEvaluationExamenTypeCommand(request.getName());
    }


    @Override
    public ICommandMessage getMessage() {
        return new CreateEvaluationExamenTypeMessage(id);
    }
}
