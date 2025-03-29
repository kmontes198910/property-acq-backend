package com.kynsof.hospitalizationService.application.command.treatmentPlan.delete;

import com.kynsof.hospitalizationService.domain.service.ITreatmentPlanService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteTreatmentPlanCommandHandler implements ICommandHandler<DeleteTreatmentPlanCommand> {

    private final ITreatmentPlanService serviceImpl;

    public DeleteTreatmentPlanCommandHandler(ITreatmentPlanService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteTreatmentPlanCommand command) {

        serviceImpl.delete(command.getId());
    }

}
