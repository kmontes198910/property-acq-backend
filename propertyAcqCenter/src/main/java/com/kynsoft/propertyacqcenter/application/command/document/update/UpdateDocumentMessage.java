package com.kynsoft.propertyacqcenter.application.command.document.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateDocumentMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_DOCUMENT";

    public UpdateDocumentMessage(UUID id) {
        this.id = id;
    }

}
