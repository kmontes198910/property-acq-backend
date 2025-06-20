package com.kynsoft.propertyacqcenter.application.command.generalDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateGeneralDocumentMessage implements ICommandMessage {
    private final UUID id;

    private final String command = "UPDATE_GENERAL_DOCUMENT";
}
