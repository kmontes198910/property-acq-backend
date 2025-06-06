package com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.create;

import lombok.Getter;

@Getter
public class CreatePropertyUploadDocumentRequest {

    private String fileName;
    private String filePath;
    private String document;
    private String property;
}
