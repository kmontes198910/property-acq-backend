package com.kynsof.hospitalizationService.application.command.emergencyCase.simpleUpdate;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SimpleUpdateEmergencyCaseMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "SIMPLE_UPDATE_EMERGENCY_CASE";

    public SimpleUpdateEmergencyCaseMessage(UUID id) {
        this.id = id;
    }

}
