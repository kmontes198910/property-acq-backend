package com.kynsoft.propertyacqcenter.application.command.generalDocument.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteGeneralDocumentMessage implements ICommandMessage {
    private final String command = "DELETE_GENERAL_DOCUMENT";

    private final UUID id;

}
