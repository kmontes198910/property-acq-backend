package com.kynsof.hospitalizationService.application.command.bed.delete;

import com.kynsof.hospitalizationService.domain.service.IBedService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteBedCommandHandler implements ICommandHandler<DeleteBedCommand> {

    private final IBedService serviceImpl;

    public DeleteBedCommandHandler(IBedService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteBedCommand command) {

        serviceImpl.delete(command.getId());
    }

}
