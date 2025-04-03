package com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.delete;

import com.kynsof.hospitalizationService.domain.service.IHospitalDischargeSummaryService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteHospitalDischargeSummaryCommandHandler implements ICommandHandler<DeleteHospitalDischargeSummaryCommand> {

    private final IHospitalDischargeSummaryService serviceImpl;

    public DeleteHospitalDischargeSummaryCommandHandler(IHospitalDischargeSummaryService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteHospitalDischargeSummaryCommand command) {

        serviceImpl.delete(command.getId());
    }

}
