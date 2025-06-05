package com.kynsoft.settings.application.command.address.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class DeleteAddressMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "DELETE_ADDRESS";

    public DeleteAddressMessage(UUID id) {
        this.id = id;
    }

}
