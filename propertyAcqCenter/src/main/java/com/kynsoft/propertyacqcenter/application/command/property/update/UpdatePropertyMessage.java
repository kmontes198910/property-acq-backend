package com.kynsoft.propertyacqcenter.application.command.property.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

@Getter
public class UpdatePropertyMessage implements ICommandMessage {

    private final String id;

    private final String command = "CREATE_PROPERTY";

    public UpdatePropertyMessage(String id) {
        this.id = id;
    }

}
