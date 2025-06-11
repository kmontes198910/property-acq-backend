package com.kynsoft.settings.application.command.serviceType.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteServiceTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_QUALIFICATION";

    public DeleteServiceTypeMessage(UUID id) {
        this.id = id;
    }

}
