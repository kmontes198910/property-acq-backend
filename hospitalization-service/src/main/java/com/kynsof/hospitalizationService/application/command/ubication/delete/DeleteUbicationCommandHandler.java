package com.kynsof.hospitalizationService.application.command.ubication.delete;

import com.kynsof.hospitalizationService.domain.service.IUbicationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteUbicationCommandHandler implements ICommandHandler<DeleteUbicationCommand> {

    private final IUbicationService ubicationService;

    public DeleteUbicationCommandHandler(IUbicationService ubicationService) {
        this.ubicationService = ubicationService;
    }

    @Override
    public void handle(DeleteUbicationCommand command) {

        ubicationService.delete(command.getId());
    }

}
