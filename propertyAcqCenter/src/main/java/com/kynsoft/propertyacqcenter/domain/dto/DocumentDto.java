package com.kynsoft.propertyacqcenter.domain.dto;

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
public class DocumentDto {
    private UUID id;
    private LegalEntityDto legalEntity;
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

    public DocumentDto(UUID id, LegalEntityDto legalEntity, String fileName, 
            String filePath, DocumentType documentType, String description, 
            Long fileSize, String contentType, LocalDate expirationDate, 
            String issuedBy, LocalDate issuingDate, DocumentStatus documentStatus,
            LocalDate verificationDate, UUID verifiedBy, String documentNumber, Boolean isOriginal, 
            String version, LocalDate renewalDate, String tags, 
            String notes, UUID createdBy, UUID updatedBy) {
        this.id = id;
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
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

}
