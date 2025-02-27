package com.kynsof.evaluation.application.command.evaluationExamenType.create;

import com.kynsof.evaluation.domain.dto.EvaluationExamenTypeDto;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.evaluation.domain.service.IEvaluationExamenTypeService;
import org.springframework.stereotype.Component;

@Component
public class CreateEvaluationExamenTypeCommandHandler  implements ICommandHandler<CreateEvaluationExamenTypeCommand> {

    private final IEvaluationExamenTypeService serviceImpl;

    public CreateEvaluationExamenTypeCommandHandler(IEvaluationExamenTypeService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(CreateEvaluationExamenTypeCommand command) {
       serviceImpl.create(new EvaluationExamenTypeDto(
                command.getId(),
                command.getName()
        ));
    }
}
