package com.kynsoft.propertyacqcenter.application.command.ownerDocument.create;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateOwnerDocumentRequest {

    private String fileName;
    private String document;
    private UUID owner;
}
