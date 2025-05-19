package com.kynsoft.propertyacqcenter.application.command.document.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.DocumentStatus;
import com.kynsoft.propertyacqcenter.domain.enums.DocumentType;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateDocumentCommand implements ICommand {

    private UUID id;
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
    private LocalDate renewalDate;
    private String tags;
    private String notes;

    public CreateDocumentCommand(UUID legalEntity, String fileName, String filePath, 
            DocumentType documentType, String description, Long fileSize, String contentType, 
            LocalDate expirationDate, String issuedBy, LocalDate issuingDate, DocumentStatus documentStatus, 
            LocalDate verificationDate, UUID verifiedBy, String documentNumber, Boolean isOriginal, 
            String version, LocalDate renewalDate, String tags, String notes) {
        this.id = UUID.randomUUID();
        this.legalEntity = legalEntity;
        this.fileName = fileName;
        this.filePath = filePath;
        this.documentType = documentType;
        this.description = description;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.expirationDate = expirationDate;
        this.issuedBy = issuedBy;
        this.issuingDate = issuingDate;
        this.documentStatus = documentStatus;
        this.verificationDate = verificationDate;
        this.verifiedBy = verifiedBy;
        this.documentNumber = documentNumber;
        this.isOriginal = isOriginal;
        this.version = version;
        this.renewalDate = renewalDate;
        this.tags = tags;
        this.notes = notes;
    }

    public static CreateDocumentCommand fromRequest(CreateDocumentRequest request) {
        return new CreateDocumentCommand(
                request.getLegalEntity(),
                request.getFileName(),
                request.getFilePath(),
                request.getDocumentType(),
                request.getDescription(),
                request.getFileSize(),
                request.getContentType(),
                request.getExpirationDate(),
                request.getIssuedBy(),
                request.getIssuingDate(),
                request.getDocumentStatus(),
                request.getVerificationDate(),
                request.getVerifiedBy(),
                request.getDocumentNumber(),
                request.getIsOriginal(),
                request.getVersion(),
                request.getRenewalDate(),
                request.getTags(),
                request.getNotes()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateDocumentMessage(id);
    }
}
