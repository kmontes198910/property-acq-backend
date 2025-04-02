package com.kynsof.hospitalizationService.application.command.ubication.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteUbicationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_UBICATION";

    public DeleteUbicationMessage(UUID id) {
        this.id = id;
    }

}
