package com.kynsof.payment.application.command.client.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateClientMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_CLIENT";

    public CreateClientMessage(UUID id) {
        this.id = id;
    }

}
