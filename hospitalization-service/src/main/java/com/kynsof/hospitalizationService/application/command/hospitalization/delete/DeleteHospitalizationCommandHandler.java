package com.kynsof.hospitalizationService.application.command.hospitalization.delete;

import com.kynsof.hospitalizationService.domain.service.IHospitalizationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteHospitalizationCommandHandler implements ICommandHandler<DeleteHospitalizationCommand> {

    private final IHospitalizationService serviceImpl;

    public DeleteHospitalizationCommandHandler(IHospitalizationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteHospitalizationCommand command) {

        serviceImpl.delete(command.getId());
    }

}
