package com.kynsof.hospitalizationService.application.command.bed.update;

import com.kynsof.hospitalizationService.domain.dto.BedDto;
import com.kynsof.hospitalizationService.domain.dto.UbicationDto;
import com.kynsof.hospitalizationService.domain.service.IBedService;
import com.kynsof.hospitalizationService.domain.service.IUbicationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateBedCommandHandler implements ICommandHandler<UpdateBedCommand> {

    private final IUbicationService ubicationService;
    private final IBedService bedService;

    public UpdateBedCommandHandler(IUbicationService ubicationService,
                                   IBedService bedService) {
        this.ubicationService = ubicationService;
        this.bedService = bedService;
    }

    @Override
    public void handle(UpdateBedCommand command) {
        UbicationDto ubicationDto = this.ubicationService.findById(command.getUbication());
        
        BedDto update = this.bedService.findById(command.getId());
        update.setCode(command.getCode());
        update.setName(command.getName());
        update.setUbication(ubicationDto);
        
        bedService.update(update);
    }
}
