package com.kynsoft.propertyacqcenter.application.command.propertyDocument.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeletePropertyDocumentMessage implements ICommandMessage {
    private final String command = "DELETE_PROPERTY_DOCUMENT";

    private final UUID id;

}
