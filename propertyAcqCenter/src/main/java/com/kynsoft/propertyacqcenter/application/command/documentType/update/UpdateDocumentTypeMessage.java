package com.kynsoft.propertyacqcenter.application.command.documentType.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateDocumentTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_DOCUMENT_TYPE";

    public UpdateDocumentTypeMessage(UUID id) {
        this.id = id;
    }

}
