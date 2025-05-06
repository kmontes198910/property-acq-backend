package com.kynsoft.propertyacqcenter.application.command.property.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

@Getter
public class CreatePropertyMessage implements ICommandMessage {

    private final String id;

    private final String command = "CREATE_PROPERTY";

    public CreatePropertyMessage(String id) {
        this.id = id;
    }

}
