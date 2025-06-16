package com.kynsoft.propertyacqcenter.application.command.documentType.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteDocumentTypeMessage implements ICommandMessage {
    private final String command = "DELETE_DOCUMENT_TYPE";

    private final UUID id;

}
