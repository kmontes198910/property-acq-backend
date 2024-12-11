package com.kynsof.treatments.application.command.vitalsigns.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.service.IVitalSignsService;
import org.springframework.stereotype.Component;

@Component
public class DeleteVitalSignsCommandHandler implements ICommandHandler<DeleteVitalSignsCommand> {

    private final IVitalSignsService vitalSignsService;

    public DeleteVitalSignsCommandHandler(IVitalSignsService vitalSignsService) {
        this.vitalSignsService = vitalSignsService;
    }

    @Override
    public void handle(DeleteVitalSignsCommand command) {
        vitalSignsService.delete(command.getId());
    }
}