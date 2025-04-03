package com.kynsof.hospitalizationService.application.command.emergencyCase.simpleUpdate;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseUpdateDto;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class SimpleUpdateEmergencyCaseCommandHandler implements ICommandHandler<SimpleUpdateEmergencyCaseCommand> {

    private final IEmergencyCaseService emergencyCaseService;

    public SimpleUpdateEmergencyCaseCommandHandler(IEmergencyCaseService emergencyCaseService) {
        this.emergencyCaseService = emergencyCaseService;
    }

    @Override
    public void handle(SimpleUpdateEmergencyCaseCommand command) {
        emergencyCaseService.simpleUpdate(new EmergencyCaseUpdateDto(
                command.getId(), 
                LocalDate.parse(command.getAdmissionDate()), 
                LocalTime.parse(command.getAdmissionTime()), 
                command.getAdmissionType(), 
                command.getStatus()
        ));
    }
}
