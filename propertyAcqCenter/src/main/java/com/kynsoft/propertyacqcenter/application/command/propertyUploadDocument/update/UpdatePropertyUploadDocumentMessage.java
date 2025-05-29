package com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdatePropertyUploadDocumentMessage implements ICommandMessage {

    private final UUID id;

    private final String command = "UPDATE_PROPERTY_UPLOAD_DOCUMENT";
}
