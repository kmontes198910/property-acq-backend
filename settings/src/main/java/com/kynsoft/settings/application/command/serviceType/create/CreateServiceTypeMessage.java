package com.kynsoft.settings.application.command.serviceType.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateServiceTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_SERVICE_TYPE";

    public CreateServiceTypeMessage(UUID id) {
        this.id = id;
    }

}
