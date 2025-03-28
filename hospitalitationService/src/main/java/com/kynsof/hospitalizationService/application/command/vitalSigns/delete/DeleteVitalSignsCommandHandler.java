package com.kynsof.hospitalizationService.application.command.vitalSigns.delete;

import com.kynsof.hospitalizationService.domain.service.IVitalSignsService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteVitalSignsCommandHandler implements ICommandHandler<DeleteVitalSignsCommand> {

    private final IVitalSignsService serviceImpl;

    public DeleteVitalSignsCommandHandler(IVitalSignsService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteVitalSignsCommand command) {

        serviceImpl.delete(command.getId());
    }

}
