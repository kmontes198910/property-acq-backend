package com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.update;

import com.kynsoft.propertyacqcenter.domain.enums.PropertyDocumentType;
import lombok.Getter;

@Getter
public class UpdatePropertyUploadDocumentRequest {

    private String fileName;
    private String filePath;
    private String document;
    private String property;
    private PropertyDocumentType documentType;
}
