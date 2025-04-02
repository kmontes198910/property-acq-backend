package com.kynsof.hospitalizationService.application.command.ubication.create;

import com.kynsof.hospitalizationService.domain.dto.UbicationDto;
import com.kynsof.hospitalizationService.domain.service.IUbicationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateUbicationCommandHandler implements ICommandHandler<CreateUbicationCommand> {

    private final IUbicationService ubicationService;

    public CreateUbicationCommandHandler(IUbicationService ubicationService) {
        this.ubicationService = ubicationService;
    }

    @Override
    public void handle(CreateUbicationCommand command) {
        this.ubicationService.create(new UbicationDto(command.getId(), command.getCode(), command.getName()));
    }
}
