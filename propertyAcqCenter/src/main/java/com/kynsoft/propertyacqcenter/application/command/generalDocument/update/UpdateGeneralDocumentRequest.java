package com.kynsoft.propertyacqcenter.application.command.generalDocument.update;

import java.util.UUID;
import lombok.Getter;

@Getter
public class UpdateGeneralDocumentRequest {
    private UUID documentType;
    private String fileName;
    private String filePath;
}
