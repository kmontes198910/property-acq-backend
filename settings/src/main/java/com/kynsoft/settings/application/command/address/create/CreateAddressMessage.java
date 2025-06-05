package com.kynsoft.settings.application.command.address.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateAddressMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_ADDRESS";

    public CreateAddressMessage(UUID id) {
        this.id = id;
    }

}
