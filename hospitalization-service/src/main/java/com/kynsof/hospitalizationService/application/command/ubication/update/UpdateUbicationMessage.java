package com.kynsof.hospitalizationService.application.command.ubication.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateUbicationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_UBICATION";

    public UpdateUbicationMessage(UUID id) {
        this.id = id;
    }

}
