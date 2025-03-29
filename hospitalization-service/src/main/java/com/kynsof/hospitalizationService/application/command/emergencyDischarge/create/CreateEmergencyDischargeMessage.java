package com.kynsof.hospitalizationService.application.command.emergencyDischarge.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateEmergencyDischargeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_EMERGENCY_DISCHARGE";

    public CreateEmergencyDischargeMessage(UUID id) {
        this.id = id;
    }

}
