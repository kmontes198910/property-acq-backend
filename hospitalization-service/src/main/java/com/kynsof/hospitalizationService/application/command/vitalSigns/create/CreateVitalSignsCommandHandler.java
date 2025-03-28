package com.kynsof.hospitalizationService.application.command.vitalSigns.create;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.VitalSignsDto;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.hospitalizationService.domain.service.IVitalSignsService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class CreateVitalSignsCommandHandler implements ICommandHandler<CreateVitalSignsCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final IVitalSignsService vitalSignsService;

    public CreateVitalSignsCommandHandler(IEmergencyCaseService emergencyCaseService,
                                          IVitalSignsService vitalSignsService) {
        this.emergencyCaseService = emergencyCaseService;
        this.vitalSignsService = vitalSignsService;
    }

    @Override
    public void handle(CreateVitalSignsCommand command) {
        EmergencyCaseDto emergencyCaseDto = this.emergencyCaseService.findById(command.getEmergencyCase());
        vitalSignsService.create(new VitalSignsDto(
                command.getId(), 
                emergencyCaseDto, 
                command.getSystolicBloodPressure(), 
                command.getDiastolicBloodPressure(), 
                command.getHeartRate(), 
                command.getRespiratoryRate(), 
                command.getTemperature(), 
                command.getWeight(), 
                command.getHeight(), 
                command.getCapillaryGlucose(), 
                command.getGlasgowScoreOcular(), 
                command.getGlasgowScoreVerbal(), 
                command.getGlasgowScoreMotor(), 
                LocalDateTime.now()
        ));
    }
}
