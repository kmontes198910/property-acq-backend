package com.kynsoft.propertyacqcenter.application.command.generalDocument.create;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateGeneralDocumentMessage implements ICommandMessage {
    private final UUID id;

    private final String command = "CREATE_GENERAL_DOCUMENT";
}
