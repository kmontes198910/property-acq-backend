package com.kynsoft.propertyacqcenter.application.command.ownerDocument.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateOwnerDocumentMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "CREATE_OWNER_DOCUMENT";
}
