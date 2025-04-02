package com.kynsof.hospitalizationService.application.command.bed.create;

import com.kynsof.hospitalizationService.domain.dto.BedDto;
import com.kynsof.hospitalizationService.domain.dto.UbicationDto;
import com.kynsof.hospitalizationService.domain.service.IBedService;
import com.kynsof.hospitalizationService.domain.service.IUbicationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateBedCommandHandler implements ICommandHandler<CreateBedCommand> {

    private final IUbicationService ubicationService;
    private final IBedService bedService;

    public CreateBedCommandHandler(IUbicationService ubicationService,
                                   IBedService bedService) {
        this.ubicationService = ubicationService;
        this.bedService = bedService;
    }

    @Override
    public void handle(CreateBedCommand command) {
        UbicationDto ubicationDto = this.ubicationService.findById(command.getUbication());
        bedService.create(new BedDto(command.getId(), command.getCode(), command.getName(), ubicationDto));
    }
}
