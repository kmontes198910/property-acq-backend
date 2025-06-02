package com.kynsoft.propertyacqcenter.application.command.ownerDocument.update;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateOwnerDocumentRequest {

    private String fileName;
    private String document;
    private UUID owner;
}
