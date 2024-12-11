package com.kynsof.treatments.application.command.vitalsigns.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateVitalSignsMessage implements ICommandMessage {

    private final UUID id;
    private final String command = "UPDATE_VITAL_SIGNS";

    public UpdateVitalSignsMessage(UUID id) {
        this.id = id;
    }
}