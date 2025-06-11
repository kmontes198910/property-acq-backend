package com.kynsoft.settings.application.command.service.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateServiceMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_SERVICE";

    public UpdateServiceMessage(UUID id) {
        this.id = id;
    }

}
