package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.contractAddendum;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class UpdateDocumentContractAddendumRequest {
    private String fileName;
    private String filePath;
    private LocalDate closingDate;
}
