package com.kynsoft.propertyacqcenter.application.command.propertyDocument.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreatePropertyDocumentMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_PROPERTY_DOCUMENT";

    public CreatePropertyDocumentMessage(UUID id) {
        this.id = id;
    }

}
