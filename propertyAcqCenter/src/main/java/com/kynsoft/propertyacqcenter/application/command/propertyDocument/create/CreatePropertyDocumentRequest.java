package com.kynsoft.propertyacqcenter.application.command.propertyDocument.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePropertyDocumentRequest {

    private String fileName;
    private String property;
    private String filePath;
}
