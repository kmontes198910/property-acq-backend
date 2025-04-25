package com.kynsoft.propertyacqcenter.application.command.document.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateDocumentMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_DOCUMENT";

    public CreateDocumentMessage(UUID id) {
        this.id = id;
    }

}
