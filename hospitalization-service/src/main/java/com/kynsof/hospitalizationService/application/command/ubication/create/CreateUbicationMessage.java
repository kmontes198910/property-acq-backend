package com.kynsof.hospitalizationService.application.command.ubication.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateUbicationMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_UBICATION";

    public CreateUbicationMessage(UUID id) {
        this.id = id;
    }

}
