package com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.create;

import com.kynsoft.propertyacqcenter.domain.enums.PropertyDocumentType;
import lombok.Getter;

@Getter
public class CreatePropertyUploadDocumentRequest {

    private String fileName;
    private String filePath;
    private String document;
    private String property;
    private PropertyDocumentType documentType;
}
