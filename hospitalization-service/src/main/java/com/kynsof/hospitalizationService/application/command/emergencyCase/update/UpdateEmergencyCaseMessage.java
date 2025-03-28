package com.kynsof.hospitalizationService.application.command.emergencyCase.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateEmergencyCaseMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_EMERGENCY_CASE";

    public UpdateEmergencyCaseMessage(UUID id) {
        this.id = id;
    }

}
