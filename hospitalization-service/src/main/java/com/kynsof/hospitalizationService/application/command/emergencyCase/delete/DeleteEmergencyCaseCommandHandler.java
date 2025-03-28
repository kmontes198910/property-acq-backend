package com.kynsof.hospitalizationService.application.command.emergencyCase.delete;

import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteEmergencyCaseCommandHandler implements ICommandHandler<DeleteEmergencyCaseCommand> {

    private final IEmergencyCaseService serviceImpl;

    public DeleteEmergencyCaseCommandHandler(IEmergencyCaseService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteEmergencyCaseCommand command) {

        serviceImpl.delete(command.getId());
    }

}
