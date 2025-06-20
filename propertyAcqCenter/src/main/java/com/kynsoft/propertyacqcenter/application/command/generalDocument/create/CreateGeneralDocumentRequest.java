package com.kynsoft.propertyacqcenter.application.command.generalDocument.create;

import java.util.UUID;
import lombok.Getter;

@Getter
public class CreateGeneralDocumentRequest {
    private UUID documentType;
    private UUID adquisitionProperty;
    private String fileName;
    private String filePath;
}
