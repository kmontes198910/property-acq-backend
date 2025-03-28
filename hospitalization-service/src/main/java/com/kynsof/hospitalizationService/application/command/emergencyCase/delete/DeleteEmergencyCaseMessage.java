package com.kynsof.hospitalizationService.application.command.emergencyCase.delete;



import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteEmergencyCaseMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_EMERGENCY_CASE";

    public DeleteEmergencyCaseMessage(UUID id) {
        this.id = id;
    }

}
