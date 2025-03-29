package com.kynsof.hospitalizationService.application.command.emergencyDischarge.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateEmergencyDischargeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_EMERGENCY_DISCHARGE";

    public UpdateEmergencyDischargeMessage(UUID id) {
        this.id = id;
    }

}
