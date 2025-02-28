package com.kynsof.evaluation.application.command.evaluationExamenType.update;

import com.kynsof.evaluation.domain.dto.EvaluationExamenTypeDto;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.evaluation.domain.service.IEvaluationExamenTypeService;
import org.springframework.stereotype.Component;

@Component
public class UpdateEvaluationExamenTypeCommandHandler implements ICommandHandler<UpdateEvaluationExamenTypeCommand> {

    private final IEvaluationExamenTypeService serviceImpl;

    public UpdateEvaluationExamenTypeCommandHandler(IEvaluationExamenTypeService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(UpdateEvaluationExamenTypeCommand command) {

        EvaluationExamenTypeDto update = this.serviceImpl.findByIds(command.getId());

        update.setName(command.getName());
        serviceImpl.create(update);
    }
}
