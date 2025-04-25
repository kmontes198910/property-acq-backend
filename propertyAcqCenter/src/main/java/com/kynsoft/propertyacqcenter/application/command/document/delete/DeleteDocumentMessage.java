package com.kynsoft.propertyacqcenter.application.command.document.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteDocumentMessage implements ICommandMessage {

    private final String command = "DELETE_DOCUMENT";

    private final UUID id;
}
