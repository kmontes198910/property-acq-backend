package com.kynsoft.propertyacqcenter.application.command.propertyDocument.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePropertyDocumentRequest {

    private String fileName;
    private String property;
    private String filePath;
}
