package com.kynsoft.settings.application.command.serviceType.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateServiceTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_PATIENT";

    public UpdateServiceTypeMessage(UUID id) {
        this.id = id;
    }

}
