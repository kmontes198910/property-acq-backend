package com.kynsoft.settings.application.command.service.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateServiceMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_SERVICE";

    public CreateServiceMessage(UUID id) {
        this.id = id;
    }

}