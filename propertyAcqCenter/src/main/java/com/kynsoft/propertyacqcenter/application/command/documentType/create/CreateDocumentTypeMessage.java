package com.kynsoft.propertyacqcenter.application.command.documentType.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateDocumentTypeMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_DOCUMENT_TYPE";

    public CreateDocumentTypeMessage(UUID id) {
        this.id = id;
    }

}
