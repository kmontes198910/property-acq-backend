package com.kynsoft.propertyacqcenter.application.command.document.update;

import com.kynsoft.propertyacqcenter.domain.enums.DocumentStatus;
import com.kynsoft.propertyacqcenter.domain.enums.DocumentType;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDocumentRequest {

    private UUID legalEntity;
    private String fileName;
    private String filePath;
    private DocumentType documentType;
    private String description;
    private Long fileSize;
    private String contentType;
    private LocalDate expirationDate;
    private String issuedBy;
    private LocalDate issuingDate;
    private DocumentStatus documentStatus;
    private LocalDate verificationDate;
    private UUID verifiedBy;
    private String documentNumber;
    private Boolean isOriginal;
    private String version;
    private Boolean renewalRequired;
    private LocalDate renewalDate;
    private String tags;
    private String notes;
}
