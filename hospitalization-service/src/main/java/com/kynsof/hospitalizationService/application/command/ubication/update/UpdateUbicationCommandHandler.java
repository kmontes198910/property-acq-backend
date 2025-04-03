package com.kynsof.hospitalizationService.application.command.ubication.update;

import com.kynsof.hospitalizationService.domain.dto.UbicationDto;
import com.kynsof.hospitalizationService.domain.service.IUbicationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateUbicationCommandHandler implements ICommandHandler<UpdateUbicationCommand> {

    private final IUbicationService ubicationService;

    public UpdateUbicationCommandHandler(IUbicationService ubicationService) {
        this.ubicationService = ubicationService;
    }

    @Override
    public void handle(UpdateUbicationCommand command) {
        UbicationDto update = this.ubicationService.findById(command.getId());
        update.setCode(command.getCode());
        update.setName(command.getName());
        this.ubicationService.update(update);
    }
}
