package com.kynsof.hospitalizationService.application.command.medicalEvolution.delete;

import com.kynsof.hospitalizationService.domain.service.IMedicalEvolutionService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteMedicalEvaluationCommandHandler implements ICommandHandler<DeleteMedicalEvaluationCommand> {

    private final IMedicalEvolutionService serviceImpl;

    public DeleteMedicalEvaluationCommandHandler(IMedicalEvolutionService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteMedicalEvaluationCommand command) {

        serviceImpl.delete(command.getId());
    }

}
