package com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeletePropertyUploadDocumentMessage implements ICommandMessage {

    private final UUID id;
    private final String command = "DELETE_PROPERTY_UPLOAD_DOCUMENT";

}
