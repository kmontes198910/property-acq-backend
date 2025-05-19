package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.DocumentDto;
import com.kynsoft.propertyacqcenter.domain.enums.DocumentStatus;
import com.kynsoft.propertyacqcenter.domain.enums.DocumentType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse implements IResponse {
    private UUID id;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private LegalEntityBasicResponse legalEntity;

    public DocumentResponse(DocumentDto documentDto) {
        this.id = documentDto.getId();
        this.fileName = documentDto.getFileName();
        this.filePath = documentDto.getFilePath();
        this.documentType = documentDto.getDocumentType();
        this.description = documentDto.getDescription();
        this.fileSize = documentDto.getFileSize();
        this.contentType = documentDto.getContentType();
        this.expirationDate = documentDto.getExpirationDate();
        this.issuedBy = documentDto.getIssuedBy();
        this.issuingDate = documentDto.getIssuingDate();
        this.documentStatus = documentDto.getDocumentStatus();
        this.verificationDate = documentDto.getVerificationDate();
        this.verifiedBy = documentDto.getVerifiedBy();
        this.documentNumber = documentDto.getDocumentNumber();
        this.isOriginal = documentDto.getIsOriginal();
        this.version = documentDto.getVersion();
        this.renewalDate = documentDto.getRenewalDate();
        this.tags = documentDto.getTags();
        this.notes = documentDto.getNotes();
        this.createdAt = documentDto.getCreatedAt();
        this.updatedAt = documentDto.getUpdatedAt();
        this.createdBy = documentDto.getCreatedBy();
        this.updatedBy = documentDto.getUpdatedBy();
        this.legalEntity = new LegalEntityBasicResponse(documentDto.getLegalEntity());
    }

}
