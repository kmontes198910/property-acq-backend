package com.kynsoft.propertyacqcenter.application.command.AdquisitionPropertyTitleCompany.create;

import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.*;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CreateDocumentRequest {
    private UUID documentType;
    private String fileName;
    private String filePath;
}
