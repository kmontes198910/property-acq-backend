package com.kynsof.treatments.application.command.vitalsigns.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.VitalSignsDto;
import com.kynsof.treatments.domain.service.IVitalSignsService;
import org.springframework.stereotype.Component;



@Component
public class UpdateVitalSignsCommandHandler implements ICommandHandler<UpdateVitalSignsCommand> {

    private final IVitalSignsService vitalSignsService;

    public UpdateVitalSignsCommandHandler(IVitalSignsService vitalSignsService) {
        this.vitalSignsService = vitalSignsService;
    }

    @Override
    public void handle(UpdateVitalSignsCommand command) {
        VitalSignsDto vitalSignsDto = vitalSignsService.findById(command.getId());

       vitalSignsDto.setBloodPressure(command.getBloodPressure());
       vitalSignsDto.setHeartRate(command.getHeartRate());
       vitalSignsDto.setTemperature(command.getTemperature());
       vitalSignsDto.setOxygenSaturation(command.getOxygenSaturation());
       vitalSignsDto.setRespiratoryRate(command.getRespiratoryRate());
       vitalSignsDto.setCranialCircumference(command.getCranialCircumference());
       vitalSignsDto.setWeight(command.getWeight());
       vitalSignsDto.setHeight(command.getTemperature());
       vitalSignsService.update(vitalSignsDto);
    }
}