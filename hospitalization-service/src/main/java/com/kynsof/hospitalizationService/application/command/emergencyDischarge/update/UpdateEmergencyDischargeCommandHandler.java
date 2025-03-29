package com.kynsof.hospitalizationService.application.command.emergencyDischarge.update;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.EmergencyDischargeDto;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.hospitalizationService.domain.service.IEmergencyDischargeService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateEmergencyDischargeCommandHandler implements ICommandHandler<UpdateEmergencyDischargeCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final IEmergencyDischargeService emergencyDischargeService;

    public UpdateEmergencyDischargeCommandHandler(IEmergencyCaseService emergencyCaseService,
                                                  IEmergencyDischargeService emergencyDischargeService) {
        this.emergencyCaseService = emergencyCaseService;
        this.emergencyDischargeService = emergencyDischargeService;
    }

    @Override
    public void handle(UpdateEmergencyDischargeCommand command) {
        EmergencyCaseDto emergencyCaseDto = this.emergencyCaseService.findById(command.getEmergencyCase());
        EmergencyDischargeDto update = this.emergencyDischargeService.findById(command.getId());

        update.setCondition(command.getCondition());
        update.setEmergencyCase(emergencyCaseDto);
        update.setEmergencyObservationRequired(command.getEmergencyObservationRequired());
        update.setExternalConsultationRequired(command.getExternalConsultationRequired());
        update.setHospitalizationRequired(command.getHospitalizationRequired());
        update.setObservations(command.getObservations());
        update.setReferralRequired(command.getReferralRequired());
        update.setRestDays(command.getRestDays());
        update.setReverseReferral(command.getReverseReferral());

        emergencyDischargeService.update(update);
    }
}
