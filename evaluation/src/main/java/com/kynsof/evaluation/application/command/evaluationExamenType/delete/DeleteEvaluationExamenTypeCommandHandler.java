package com.kynsof.evaluation.application.command.evaluationExamenType.delete;

import com.kynsof.evaluation.domain.service.IEvaluationExamenTypeService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteEvaluationExamenTypeCommandHandler implements ICommandHandler<DeleteEvaluationExamenTypeCommand> {

    private final IEvaluationExamenTypeService serviceImpl;

    public DeleteEvaluationExamenTypeCommandHandler(IEvaluationExamenTypeService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteEvaluationExamenTypeCommand command) {
        serviceImpl.delete(command.getId());
    }

}
