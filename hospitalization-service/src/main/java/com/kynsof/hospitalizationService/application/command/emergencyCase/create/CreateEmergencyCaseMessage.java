package com.kynsof.hospitalizationService.application.command.emergencyCase.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateEmergencyCaseMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_EMERGENCY_CASE";

    public CreateEmergencyCaseMessage(UUID id) {
        this.id = id;
    }

}
