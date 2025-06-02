package com.kynsoft.wamessaging.application.command.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateWhatsAppMessageMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_MESSAGE";

    public CreateWhatsAppMessageMessage(UUID id) {
        this.id = id;
    }

}
