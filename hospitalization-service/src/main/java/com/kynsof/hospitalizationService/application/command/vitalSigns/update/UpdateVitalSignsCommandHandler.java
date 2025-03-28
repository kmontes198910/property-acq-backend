package com.kynsof.hospitalizationService.application.command.vitalSigns.update;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.VitalSignsDto;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.hospitalizationService.domain.service.IVitalSignsService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateVitalSignsCommandHandler implements ICommandHandler<UpdateVitalSignsCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final IVitalSignsService vitalSignsService;

    public UpdateVitalSignsCommandHandler(IEmergencyCaseService emergencyCaseService,
                                          IVitalSignsService vitalSignsService) {
        this.emergencyCaseService = emergencyCaseService;
        this.vitalSignsService = vitalSignsService;
    }

    @Override
    public void handle(UpdateVitalSignsCommand command) {
        EmergencyCaseDto emergencyCaseDto = this.emergencyCaseService.findById(command.getEmergencyCase());
        VitalSignsDto vitalSignsDto = this.vitalSignsService.findById(command.getId());

        vitalSignsDto.setEmergencyCase(emergencyCaseDto);
        vitalSignsDto.setSystolicBloodPressure(command.getSystolicBloodPressure());
        vitalSignsDto.setDiastolicBloodPressure(command.getDiastolicBloodPressure());
        vitalSignsDto.setHeartRate(command.getHeartRate());
        vitalSignsDto.setRespiratoryRate(command.getRespiratoryRate());
        vitalSignsDto.setTemperature(command.getTemperature());
        vitalSignsDto.setWeight(command.getWeight());
        vitalSignsDto.setHeight(command.getHeight());
        vitalSignsDto.setCapillaryGlucose(command.getCapillaryGlucose());
        vitalSignsDto.setGlasgowScoreMotor(command.getGlasgowScoreMotor());
        vitalSignsDto.setGlasgowScoreOcular(command.getGlasgowScoreOcular());
        vitalSignsDto.setGlasgowScoreVerbal(command.getGlasgowScoreVerbal());
        
        vitalSignsService.update(vitalSignsDto);
    }
}
