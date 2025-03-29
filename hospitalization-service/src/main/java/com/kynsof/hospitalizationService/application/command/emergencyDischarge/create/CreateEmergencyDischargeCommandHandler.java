package com.kynsof.hospitalizationService.application.command.emergencyDischarge.create;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.EmergencyDischargeDto;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.hospitalizationService.domain.service.IEmergencyDischargeService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateEmergencyDischargeCommandHandler implements ICommandHandler<CreateEmergencyDischargeCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final IEmergencyDischargeService emergencyDischargeService;

    public CreateEmergencyDischargeCommandHandler(IEmergencyCaseService emergencyCaseService,
                                                  IEmergencyDischargeService emergencyDischargeService) {
        this.emergencyCaseService = emergencyCaseService;
        this.emergencyDischargeService = emergencyDischargeService;
    }

    @Override
    public void handle(CreateEmergencyDischargeCommand command) {
        EmergencyCaseDto emergencyCaseDto = this.emergencyCaseService.findById(command.getEmergencyCase());
        emergencyDischargeService.create(new EmergencyDischargeDto(
                command.getId(), 
                emergencyCaseDto, 
                command.getCondition(), 
                command.getHospitalizationRequired(), 
                command.getExternalConsultationRequired(), 
                command.getEmergencyObservationRequired(), 
                command.getReferralRequired(), 
                command.getReverseReferral(), 
                command.getObservations(), 
                command.getRestDays()
        ));
    }
}
