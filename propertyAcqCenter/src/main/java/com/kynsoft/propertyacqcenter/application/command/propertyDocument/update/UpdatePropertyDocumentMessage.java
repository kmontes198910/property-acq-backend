package com.kynsoft.propertyacqcenter.application.command.propertyDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdatePropertyDocumentMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_PROPERTY_DOCUMENT";

    public UpdatePropertyDocumentMessage(UUID id) {
        this.id = id;
    }

}
