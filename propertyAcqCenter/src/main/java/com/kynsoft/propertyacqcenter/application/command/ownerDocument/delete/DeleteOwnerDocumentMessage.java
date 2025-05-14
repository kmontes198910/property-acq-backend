package com.kynsoft.propertyacqcenter.application.command.ownerDocument.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteOwnerDocumentMessage implements ICommandMessage {
    private final String command = "DELETE_OWNER_DOCUMENT";

    private final UUID id;

}
