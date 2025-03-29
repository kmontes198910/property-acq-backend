package com.kynsof.hospitalizationService.application.command.emergencyDischarge.delete;

import com.kynsof.hospitalizationService.domain.service.IEmergencyDischargeService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteEmergencyDischargeCommandHandler implements ICommandHandler<DeleteEmergencyDischargeCommand> {

    private final IEmergencyDischargeService serviceImpl;

    public DeleteEmergencyDischargeCommandHandler(IEmergencyDischargeService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(DeleteEmergencyDischargeCommand command) {

        serviceImpl.delete(command.getId());
    }

}
