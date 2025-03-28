package com.kynsof.hospitalizationService.application.command.vitalSigns.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteVitalSignsMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_VITAL_SIGNS";

    public DeleteVitalSignsMessage(UUID id) {
        this.id = id;
    }

}
